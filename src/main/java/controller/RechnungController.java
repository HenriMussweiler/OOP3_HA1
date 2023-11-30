package controller;

import core.model.Ausleihvorgang;
import core.model.Rechnung;
import core.model.Teilnehmer;
import core.service.AusleihvorgangService;
import core.service.RechnungService;
import core.service.TeilnehmerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RechnungController {

    @FXML
    public Button zurueckButton;

    @FXML
    private ComboBox<String> teilnehmerComboBox;

    @FXML
    private DatePicker startdatumPicker;

    @FXML
    private DatePicker enddatumPicker;

    @FXML
    private Button generiereRechnungButton;

    private AusleihvorgangService ausleihvorgangService;
    private TeilnehmerService teilnehmerService;

    @FXML
    private void initialize() {
        initComboBoxes();
    }

    private void initComboBoxes() {
        ObservableList<String> teilnehmerList = FXCollections.observableArrayList();

        try {
            ausleihvorgangService = new AusleihvorgangService();
            List<Ausleihvorgang> ausleihvorgänge = ausleihvorgangService.findAll();
            for (Ausleihvorgang ausleihvorgang : ausleihvorgänge) {
                if (ausleihvorgang.getAbgeschlossen() || ausleihvorgang.getStorniert() && ausleihvorgang.getRechnung() == null) {
                    teilnehmerList.add(ausleihvorgang.getTeilnehmer().getName());
                }
            }
        } catch (Exception e) {
            System.out.println("Fehler beim Laden der Teilnehmer: " + e.getMessage());
        }

        try {
            teilnehmerComboBox.setItems(FXCollections.observableArrayList(teilnehmerList));
        } catch (Exception e) {
            System.out.println("Fehler beim Laden der Teilnehmer: " + e.getMessage());
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void generiereRechnungButtonClicked(ActionEvent actionEvent) {
        teilnehmerService = new TeilnehmerService();
        String teilnehmer = teilnehmerComboBox.getValue();
        Teilnehmer selectedTeilnehmer = teilnehmerService.find(teilnehmerService.findAll().stream()
                .filter(teilnehmer1 -> teilnehmer1.getName().equals(teilnehmer))
                .map(Teilnehmer::getTeilnehmerId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Teilnehmer nicht gefunden")));
        LocalDate startdatum = startdatumPicker.getValue();
        LocalDate enddatum = enddatumPicker.getValue();

        if (selectedTeilnehmer == null || startdatum == null || enddatum == null) {
            showErrorAlert("Bitte füllen Sie alle Felder aus.");
            return;
        }

        generateInvoice(selectedTeilnehmer, startdatum, enddatum);

        //Felder leeren
        teilnehmerComboBox.setValue(null);
        startdatumPicker.setValue(null);
        enddatumPicker.setValue(null);

        initComboBoxes();
    }

    private void generateInvoice(Teilnehmer teilnehmer, LocalDate startdatum, LocalDate enddatum) {
        double totalAmount = calculateTotalAmount(teilnehmer, startdatum, enddatum);
        String invoiceText = createInvoiceText(teilnehmer, startdatum, enddatum, totalAmount);
        RechnungService rechnungService = new RechnungService();
        LocalDateTime rechnungsdatum = LocalDateTime.now();
        Rechnung rechnung = new Rechnung(teilnehmer, rechnungsdatum, totalAmount);

        List<Ausleihvorgang> relevanteAusleihvorgaenge = getRelevantAusleihvorgaenge(teilnehmer, startdatum, enddatum);
        for (Ausleihvorgang ausleihvorgang : relevanteAusleihvorgaenge) {
            rechnung.addAusleihvorgang(ausleihvorgang);
        }

        if (relevanteAusleihvorgaenge.isEmpty()) {
            showErrorAlert("Keine Ausleihvorgänge gefunden.");
            return;
        }

        rechnungService.save(rechnung);
        for (Ausleihvorgang ausleihvorgang : relevanteAusleihvorgaenge) {
            ausleihvorgangService.save(ausleihvorgang);
        }

        showAlert("Rechnung generiert:\n" + invoiceText);
    }

    private List<Ausleihvorgang> getRelevantAusleihvorgaenge(Teilnehmer teilnehmer, LocalDate startdatum, LocalDate enddatum) {
        ausleihvorgangService = new AusleihvorgangService();
        List<Ausleihvorgang> ausleihvorgaenge = ausleihvorgangService.findAll();
        List<Ausleihvorgang> ausleihenVonTeilnehmer = new ArrayList<>();

        try {
            for (Ausleihvorgang ausleihvorgang : ausleihvorgaenge) {

                if (ausleihvorgang.getTeilnehmer().getTeilnehmerId() == (teilnehmer.getTeilnehmerId())) {
                    if (ausleihvorgang.getAbgeschlossen() || ausleihvorgang.getStorniert()) {
                        if (ausleihvorgang.getRechnung() == null) {
                            if (ausleihvorgang.getStartdatum().isAfter(startdatum.atTime(0, 0, 0)) && ausleihvorgang.getEnddatum().isBefore(enddatum.atTime(23, 59, 59)) || ausleihvorgang.getStartdatum().isEqual(startdatum.atTime(0, 0, 0)) || ausleihvorgang.getEnddatum().isEqual(enddatum.atTime(23, 59, 59))) {
                                ausleihenVonTeilnehmer.add(ausleihvorgang);
                            }
                        }
                    }
                }
            }
            return ausleihenVonTeilnehmer;
        } catch (Exception e) {
            System.out.println("Fehler beim Filtern der Ausleihvorgänge: " + e.getMessage());
        }
        return null;
    }

    private double calculateTotalAmount(Teilnehmer teilnehmer, LocalDate startdatum, LocalDate enddatum) {
        List<Ausleihvorgang> ausleihenVonTeilnehmer = getRelevantAusleihvorgaenge(teilnehmer, startdatum, enddatum);
        double totalAmount = 0;

        //TotalAmount berechnen (Gefahrene Kilometer * 0.8 und Stornierte Ausleihen mit fix 50€)
        for (Ausleihvorgang ausleihvorgang : ausleihenVonTeilnehmer) {
            if (ausleihvorgang.getStorniert()) {
                totalAmount += 50;
            } else if (ausleihvorgang.getAbgeschlossen()) {
                totalAmount += ausleihvorgang.getGefahreneKilometer() * 0.8;
            }
        }

        //TotalAmount auf 2 Nachkommastellen runden
        totalAmount = Math.round(totalAmount * 100.0) / 100.0;

        return totalAmount;
    }


    private String createInvoiceText(Teilnehmer teilnehmer, LocalDate startdatum, LocalDate enddatum, double totalAmount) {
        String invoiceText = "Rechnung für " + teilnehmer.getVorname() + " " + teilnehmer.getName() + "\n" +
                "Zeitraum: " + startdatum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " + enddatum.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "\n" +
                "Gesamtbetrag: " + totalAmount + "€";
        return invoiceText;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Rechnung generiert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void zurueckButtonClicked() {
        // Hier kannst du die Logik für den "Zurück"-Button implementieren
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setPrimaryStage((Stage) zurueckButton.getScene().getWindow());

            Scene scene = new Scene(root);
            Stage stage = (Stage) zurueckButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
