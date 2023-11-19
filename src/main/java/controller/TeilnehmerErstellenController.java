package controller;

import core.model.Teilnehmer;
import core.service.TeilnehmerService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeilnehmerErstellenController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField vornameField;

    @FXML
    private TextField strasseField;

    @FXML
    private TextField hausnummerField;

    @FXML
    private TextField plzField;

    @FXML
    private TextField ortField;

    @FXML
    private TextField ibanField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField telefonField;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private ComboBox<String> sharingComboBox;

    TeilnehmerService teilnehmerService = new TeilnehmerService();

    private TeilnehmerController teilnehmerController;

    public void setTeilnehmerController(TeilnehmerController teilnehmerController) {
        this.teilnehmerController = teilnehmerController;
    }

    @FXML
    private void speichernButtonClicked() {
        // Implementiere die Logik für das Speichern des Teilnehmers
        try {
            String name = nameField.getText();
            String vorname = vornameField.getText();
            String strasse = strasseField.getText();
            String hausnummer = hausnummerField.getText();
            String plz = plzField.getText();
            String ort = ortField.getText();
            String iban = ibanField.getText();
            String mail = mailField.getText();
            String telefon = telefonField.getText();

            //Check if all fields are filled
            if (name.isEmpty() || vorname.isEmpty() || strasse.isEmpty() || hausnummer.isEmpty() || plz.isEmpty() || ort.isEmpty() || iban.isEmpty() || mail.isEmpty() || telefon.isEmpty()) {
                showAlert("Bitte füllen Sie alle Felder aus.");
                return;
            } else {
                //Create new Teilnehmer
                Teilnehmer newTeilnehmer = new Teilnehmer(name, vorname, strasse, hausnummer, plz, ort, iban, mail, telefon);

                //Save new Teilnehmer
                teilnehmerService.save(newTeilnehmer);
                showAlert("Teilnehmer erfolgreich angelegt.");

                //TableView aktualisieren
                teilnehmerController.initTeilnehmerTableView();

                //ComboBox aktualisieren
                teilnehmerController.initTeilnehmerIdComboBox();

                //Clear all fields
                clearFields();
            }

        } catch (Exception e) {
            showAlert("Teilnehmer konnte nicht angelegt werden.");
        }
    }

    private void clearFields() {
        nameField.clear();
        vornameField.clear();
        strasseField.clear();
        hausnummerField.clear();
        plzField.clear();
        ortField.clear();
        ibanField.clear();
        mailField.clear();
        telefonField.clear();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void zurueckButtonClicked() {
        // Implementiere die Logik für den Zurück-Button
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }
}
