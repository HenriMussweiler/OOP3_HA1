package controller;

import core.model.Ausleihvorgang;
import core.model.Fahrzeug;
import core.model.Teilnehmer;
import core.service.AusleihvorgangService;
import core.service.IFahrzeugService;
import core.service.ITeilnehmerService;
import core.service.TeilnehmerService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TeilnehmerController {

    @FXML
    private Button hinzufuegenButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private TableView<Teilnehmer> teilnehmerTableView;

    @FXML
    private TableColumn<Teilnehmer, Long> teilnehmerIdColumn;

    @FXML
    private TableColumn<Teilnehmer, String> nameColumn;

    @FXML
    private TableColumn<Teilnehmer, String> vornameColumn;

    @FXML
    private TableColumn<Teilnehmer, String> strasseColumn;

    @FXML
    private TableColumn<Teilnehmer, String> hausnummerColumn;

    @FXML
    private TableColumn<Teilnehmer, String> plzColumn;

    @FXML
    private TableColumn<Teilnehmer, String> ortColumn;

    @FXML
    private TableColumn<Teilnehmer, String> ibanColumn;

    @FXML
    private TableColumn<Teilnehmer, String> mailColumn;

    @FXML
    private TableColumn<Teilnehmer, String> telefonColumn;

    @FXML
    private ComboBox<Long> teilnehmerIdComboBox;

    @FXML
    private Button aendernButton;

    private ITeilnehmerService<Teilnehmer> teilnehmerService;

    private AusleihvorgangService ausleihvorangService = new AusleihvorgangService();

    @FXML
    private void hinzufuegenButtonClicked() {
        // Implementiere die Logik zum Hinzufügen eines Teilnehmers
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/teilnehmerErstellen.fxml"));
            Parent root = loader.load();

            TeilnehmerErstellenController controller = loader.getController();
            controller.setTeilnehmerController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(hinzufuegenButton.getScene().getWindow());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void aendernButtonClicked() {
        //Prüfen ob Teilnehmer ausgewählt wurde
        if (teilnehmerIdComboBox.getValue() == null) {
            showErrorAlert("Bitte wählen Sie einen Teilnehmer aus.");
            return;
        }

        // Lade den ausgewählten Teilnehmer aus der Datenquelle
        teilnehmerService = new TeilnehmerService();
        Teilnehmer teilnehmer = teilnehmerService.find(teilnehmerIdComboBox.getValue());


        // Öffne das Fenster zum Ändern des Teilnehmers
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/teilnehmerAendern.fxml"));
            Parent root = loader.load();

            TeilnehmerAendernController controller = loader.getController();
            controller.setTeilnehmerController(this);
            controller.setSelectedTeilnehmer(teilnehmer);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aendernButton.getScene().getWindow());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loeschenButtonClicked() {
        teilnehmerService = new TeilnehmerService();
        try {
           //Teilnehmer aus der Datenquelle laden
           Teilnehmer teilnehmer = teilnehmerService.find(teilnehmerIdComboBox.getValue());

           //Prüfen ob Teilnehmer noch in aktive Ausleihen involviert ist
           if (isTeilnehmerInAktivenAusleihen(teilnehmer)) {
               showErrorAlert("Der Teilnehmer ist noch in aktiven Ausleihen involviert und kann daher nicht gelöscht werden.");
               return;
           }



            //Fragen ob wirklich gelöscht werden soll
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Teilnehmer löschen");
            alert.setHeaderText("Teilnehmer löschen");
            alert.setContentText("Möchten Sie den Teilenhmer wirklich löschen?");
            alert.showAndWait();

            if (alert.getResult() != ButtonType.OK) {
                return;
            }


            //Teilnehmer löschen
            teilnehmerService.delete(teilnehmer.getTeilnehmerId());
            showAlert("Teilnehmer erfolgreich gelöscht.");
        } catch (Exception e) {
            showAlert("Teilnehmer konnte nicht gelöscht werden.");
        }
    }

    private boolean isTeilnehmerInAktivenAusleihen(Teilnehmer teilnehmer) {
        //Prüfe ob Teilnehmer in aktiven Ausleihen involviert ist
        List<Ausleihvorgang> ausleihvorgaenge = ausleihvorangService.findAll();
        for (Ausleihvorgang ausleihvorgang : ausleihvorgaenge) {
            if (ausleihvorgang.getTeilnehmer().getTeilnehmerId() == teilnehmer.getTeilnehmerId()) {
                //Prüfen ob das Enddatum des Ausleihvorgangs vor dem aktuellen Datum liegt
                if (ausleihvorgang.getEnddatum().isAfter(java.time.LocalDateTime.now())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Initialisierung, wenn nötig
        initTeilnehmerTableView();
        initTeilnehmerIdComboBox();

        //Testdaten hinzufügen
        //createAndSaveTestdaten();
    }

    public void createAndSaveTestdaten() {
        teilnehmerService = new TeilnehmerService();
            //Erstelle Testdaten
            Teilnehmer teilnehmer1 = new Teilnehmer("Mustermann", "Max", "Musterstraße", "1", "12345", "Musterstadt", "DE123456789", "max.mustermann@muster.de", "0123456789");
            Teilnehmer teilnehmer2 = new Teilnehmer("Musterfrau", "Maria", "Musterstraße", "2", "12345", "Musterstadt", "DE987654321", "maria.musterfrau@muster.de", "9876543210");

            //Speichere Testdaten
            teilnehmerService.save(teilnehmer1);
            teilnehmerService.save(teilnehmer2);
    }

    protected void initTeilnehmerTableView() {
        teilnehmerService = new TeilnehmerService();
        //TableView leeren
        teilnehmerTableView.getItems().clear();

        // Erstelle eine leere Liste für die Teilnehmer
        ObservableList<Teilnehmer> teilnehmer = FXCollections.observableArrayList();

        // Lade die Teilnehmer aus der Datenquelle
        teilnehmer.addAll(teilnehmerService.findAll());

        // Füge die Teilnehmer der TableView hinzu
        teilnehmerTableView.setItems(teilnehmer);

        // Erstelle die Spalten und verknüpfe sie mit den Attributen der Teilnehmer
        teilnehmerIdColumn.setCellValueFactory(new PropertyValueFactory<>("teilnehmerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        strasseColumn.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        hausnummerColumn.setCellValueFactory(new PropertyValueFactory<>("hausnummer"));
        plzColumn.setCellValueFactory(new PropertyValueFactory<>("postleitzahl"));
        ortColumn.setCellValueFactory(new PropertyValueFactory<>("ort"));
        ibanColumn.setCellValueFactory(new PropertyValueFactory<>("iban"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
    }

    protected void initTeilnehmerIdComboBox() {
        ObservableList<Long> teilnehmerIds = FXCollections.observableArrayList(teilnehmerService.findAll().stream()
                .map(Teilnehmer::getTeilnehmerId)
                .collect(Collectors.toList())
        );
        teilnehmerIdComboBox.setItems(teilnehmerIds);
    }

    public ITeilnehmerService<Teilnehmer> getTeilnehmerService() {
        return teilnehmerService;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void aktualisierenButtonClicked(ActionEvent actionEvent) {
        initialize();
    }
}
