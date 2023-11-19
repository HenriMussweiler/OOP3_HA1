package controller;

import core.model.Teilnehmer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeilnehmerAendernController {

    private TeilnehmerController teilnehmerController;
    private Teilnehmer selectedTeilnehmer;

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

    public void setTeilnehmerController(TeilnehmerController teilnehmerController) {
        this.teilnehmerController = teilnehmerController;
    }

    public void setSelectedTeilnehmer(Teilnehmer selectedTeilnehmer) {
        this.selectedTeilnehmer = selectedTeilnehmer;
        fillFields();
    }

    private void fillFields() {
        if (selectedTeilnehmer != null) {
            nameField.setText(selectedTeilnehmer.getName());
            vornameField.setText(selectedTeilnehmer.getVorname());
            strasseField.setText(selectedTeilnehmer.getStrasse());
            hausnummerField.setText(selectedTeilnehmer.getHausnummer());
            plzField.setText(selectedTeilnehmer.getPostleitzahl());
            ortField.setText(selectedTeilnehmer.getOrt());
            ibanField.setText(selectedTeilnehmer.getIban());
            mailField.setText(selectedTeilnehmer.getMail());
            telefonField.setText(selectedTeilnehmer.getTelefon());
        }
    }

    @FXML
    private void speichernButtonClicked() {
        try {
            // Aktualisiere die Werte des ausgewählten Teilnehmers
            selectedTeilnehmer.setName(nameField.getText());
            selectedTeilnehmer.setVorname(vornameField.getText());
            selectedTeilnehmer.setStrasse(strasseField.getText());
            selectedTeilnehmer.setHausnummer(hausnummerField.getText());
            selectedTeilnehmer.setPostleitzahl(plzField.getText());
            selectedTeilnehmer.setOrt(ortField.getText());
            selectedTeilnehmer.setIban(ibanField.getText());
            selectedTeilnehmer.setMail(mailField.getText());
            selectedTeilnehmer.setTelefon(telefonField.getText());

            // Aktualisiere den Teilnehmer in der Datenbank
            teilnehmerController.getTeilnehmerService().update(selectedTeilnehmer);

            // Aktualisiere die TableView im TeilnehmerController
            teilnehmerController.initTeilnehmerTableView();

            // Schließe das Fenster
            closeWindow();
        } catch (Exception e) {
            showAlert("Teilnehmer konnte nicht aktualisiert werden.");
        }
    }

    @FXML
    private void zurueckButtonClicked() {
        // Schließe das Fenster
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
