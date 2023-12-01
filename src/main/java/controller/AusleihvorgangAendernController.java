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
import jfxtras.scene.control.LocalDateTimeTextField;

public class AusleihvorgangAendernController {

    public TextField gefahreneKilometerTextField;
    @FXML
    private ComboBox<Teilnehmer> teilnehmerComboBox;

    @FXML
    private ComboBox<Fahrzeug> fahrzeugComboBox;

    @FXML
    private LocalDateTimeTextField startdatumPicker;

    @FXML
    private LocalDateTimeTextField enddatumPicker;

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
        //Prüfen ob alle Felder ausgefüllt sind
        if (teilnehmerComboBox.getValue() == null || fahrzeugComboBox.getValue() == null || startdatumPicker.getLocalDateTime() == null || enddatumPicker.getLocalDateTime() == null || gefahreneKilometerTextField.getText().isEmpty()) {
            showAlert("Bitte füllen Sie alle Felder aus.");
            return;
        }

        //Prüfen ob Startdatum vor Enddatum liegt
        if (startdatumPicker.getLocalDateTime().isAfter(enddatumPicker.getLocalDateTime())) {
            showAlert("Das Startdatum muss vor dem Enddatum liegen.");
            return;
        }

        //Prüfen ob Gefahrene Kilometer eine Zahl ist
        try {
            Integer.parseInt(gefahreneKilometerTextField.getText());
        } catch (NumberFormatException e) {
            showAlert("Gefahrene Kilometer muss eine Zahl sein.");
            return;
        }

        //Zugriff auf ausgewählten Teilnehmer und Fahrzeug
        Teilnehmer teilnehmer = teilnehmerComboBox.getValue();
        Fahrzeug fahrzeug = fahrzeugComboBox.getValue();

        // Logik für das Speichern des geänderten Ausleihvorgangs
        if (teilnehmer != null && fahrzeug != null) {
            selectedAusleihvorgang.setTeilnehmer(teilnehmer);
            selectedAusleihvorgang.setFahrzeug(fahrzeug);
            selectedAusleihvorgang.setStartdatum(startdatumPicker.getLocalDateTime());
            selectedAusleihvorgang.setEnddatum(enddatumPicker.getLocalDateTime());
            selectedAusleihvorgang.setGefahreneKilometer(Integer.parseInt(gefahreneKilometerTextField.getText()));

            // Speichern des geänderten Ausleihvorgangs
            selectedAusleihvorgang = ausleihvorgangService.update(selectedAusleihvorgang);

            //Lade Daten des Fahrzeugs
            Fahrzeug fahrzeug1 = fahrzeugService.find(fahrzeug.getFahrzeugId());



            try {
                fahrzeug1.setKilometerstand(fahrzeug1.getKilometerstand() + Integer.parseInt(gefahreneKilometerTextField.getText()));
            } catch (NumberFormatException e) {
                showAlert("Gefahrene Kilometer muss eine Zahl sein.");
                return;
            }

            // Gefahrene Kilometer des Fahrzeugs aktualisieren
            fahrzeugService.update(fahrzeug1);

            //Ausleihvorgang auf abgeschlossen setzen
            selectedAusleihvorgang.setAbgeschlossen(true);
            ausleihvorgangService.update(selectedAusleihvorgang);

            // Schließen des Fensters
            Stage stage = (Stage) speichernButton.getScene().getWindow();
            stage.close();
            // Anzeigen einer Information
            showAlert("Ausleihvorgang wurde geändert.");

            ausleihvorgangController.initialize();
        } else {
            showAlert("Ausleihvorgang konnte nicht geändert werden.");
        }
    }

    @FXML
    private void zurueckButtonClicked(ActionEvent event) {
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

            //TextField mit Daten füllen
            gefahreneKilometerTextField.setText(String.valueOf(selectedAusleihvorgang.getGefahreneKilometer()));

            // Zellen für die Anzeige konfigurieren
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

            // Werte für die Anzeige konfigurieren
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
                    return null;
                }
            });

            // Auswahl des ausgewählten Elements basierend auf Gleichheit konfigurieren
            teilnehmerComboBox.getSelectionModel().select(selectedAusleihvorgang.getTeilnehmer());
            fahrzeugComboBox.getSelectionModel().select(selectedAusleihvorgang.getFahrzeug());


            // Anzeige des Start- und Enddatums konfigurieren
            startdatumPicker.setLocalDateTime(selectedAusleihvorgang.getStartdatum());
            enddatumPicker.setLocalDateTime(selectedAusleihvorgang.getEnddatum());
        }
    }

    public void setAusleihvorgangController(AusleihvorgangController ausleihvorgangController) {
        this.ausleihvorgangController = ausleihvorgangController;
    }

    public void setSelectedAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        this.selectedAusleihvorgang = ausleihvorgang;
    }
}
