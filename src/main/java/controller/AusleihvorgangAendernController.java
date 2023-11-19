package controller;

import core.model.Ausleihvorgang;
import core.model.Fahrzeug;
import core.model.Teilnehmer;
import core.service.AusleihvorgangService;
import core.service.FahrzeugService;
import core.service.TeilnehmerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class AusleihvorgangAendernController {

    @FXML
    private ComboBox<Teilnehmer> teilnehmerComboBox;

    @FXML
    private ComboBox<Fahrzeug> fahrzeugComboBox;

    @FXML
    private DatePicker startdatumPicker;

    @FXML
    private DatePicker enddatumPicker;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    private Ausleihvorgang selectedAusleihvorgang;

    private TeilnehmerService teilnehmerService = new TeilnehmerService();
    private FahrzeugService fahrzeugService = new FahrzeugService();
    private AusleihvorgangService ausleihvorgangService = new AusleihvorgangService();
    private AusleihvorgangController ausleihvorgangController;

    @FXML
    private void initialize() {
        populateFields();
    }

    @FXML
    private void speichernButtonClicked(ActionEvent event) {
        //Zugriff auf ausgewählten Teilnehmer und Fahrzeug
        Teilnehmer teilnehmer = teilnehmerComboBox.getValue();
        Fahrzeug fahrzeug = fahrzeugComboBox.getValue();

        // Logik für das Speichern des geänderten Ausleihvorgangs
        if (teilnehmer != null && fahrzeug != null) {
            selectedAusleihvorgang.setTeilnehmer(teilnehmer);
            selectedAusleihvorgang.setFahrzeug(fahrzeug);
            selectedAusleihvorgang.setStartdatum(startdatumPicker.getValue().atStartOfDay());
            selectedAusleihvorgang.setEnddatum(enddatumPicker.getValue().atStartOfDay());

            // Speichern des geänderten Ausleihvorgangs
            selectedAusleihvorgang = ausleihvorgangService.update(selectedAusleihvorgang);
            // Schließen des Fensters
            Stage stage = (Stage) speichernButton.getScene().getWindow();
            stage.close();
            // Anzeigen einer Information
            showAlert("Ausleihvorgang wurde geändert.");
        } else {
            showAlert("Ausleihvorgang konnte nicht geändert werden.");
        }
    }

    @FXML
    private void zurueckButtonClicked(ActionEvent event) {
        // Hier kannst du die Logik für den Zurück-Button implementieren
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        this.selectedAusleihvorgang = ausleihvorgang;
        populateFields();
    }

    private void populateFields() {
        if (selectedAusleihvorgang != null) {
            // Teilnehmer und Fahrzeug Daten laden
            ObservableList<Teilnehmer> teilnehmerList = FXCollections.observableArrayList(teilnehmerService.findAll());
            ObservableList<Fahrzeug> fahrzeugList = FXCollections.observableArrayList(fahrzeugService.findAll());

            // ComboBoxen mit Daten füllen
            teilnehmerComboBox.setItems(teilnehmerList);
            fahrzeugComboBox.setItems(fahrzeugList);

            // Zellenfabrik für die Anzeige konfigurieren
            teilnehmerComboBox.setCellFactory(param -> new ListCell<Teilnehmer>() {
                @Override
                protected void updateItem(Teilnehmer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getVorname() + " " + item.getName());
                    }
                }
            });

            fahrzeugComboBox.setCellFactory(param -> new ListCell<Fahrzeug>() {
                @Override
                protected void updateItem(Fahrzeug item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.getHersteller() + " " + item.getModell());
                    }
                }
            });

            // Wertfabrik für die Anzeige konfigurieren
            teilnehmerComboBox.setConverter(new StringConverter<Teilnehmer>() {
                @Override
                public String toString(Teilnehmer item) {
                    return item == null ? null : item.getVorname() + " " + item.getName();
                }

                @Override
                public Teilnehmer fromString(String string) {
                    // Du kannst hier die Logik für die Umwandlung von String zu Teilnehmer implementieren,
                    // falls dies erforderlich ist (z. B. wenn du die Teilnehmerliste bearbeiten kannst).
                    return null;
                }
            });

            fahrzeugComboBox.setConverter(new StringConverter<Fahrzeug>() {
                @Override
                public String toString(Fahrzeug item) {
                    return item == null ? null : item.getHersteller() + " " + item.getModell();
                }

                @Override
                public Fahrzeug fromString(String string) {
                    // Du kannst hier die Logik für die Umwandlung von String zu Fahrzeug implementieren,
                    // falls dies erforderlich ist (z. B. wenn du die Fahrzeugliste bearbeiten kannst).
                    return null;
                }
            });

            // Auswahl des ausgewählten Elements basierend auf Gleichheit konfigurieren
            teilnehmerComboBox.getSelectionModel().select(selectedAusleihvorgang.getTeilnehmer());
            fahrzeugComboBox.getSelectionModel().select(selectedAusleihvorgang.getFahrzeug());

            startdatumPicker.setValue(selectedAusleihvorgang.getStartdatum().toLocalDate());
            enddatumPicker.setValue(selectedAusleihvorgang.getEnddatum().toLocalDate());
        }
    }

    public void setAusleihvorgangController(AusleihvorgangController ausleihvorgangController) {
        // Hier kannst du die Logik für die Zuweisung des AusleihvorgangControllers implementieren
        this.ausleihvorgangController = ausleihvorgangController;
    }

    public void setSelectedAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        // Hier kannst du die Logik für die Zuweisung des ausgewählten Ausleihvorgangs implementieren
        this.selectedAusleihvorgang = ausleihvorgang;
    }
}