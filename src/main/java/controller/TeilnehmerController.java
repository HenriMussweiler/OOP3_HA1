package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class TeilnehmerController {

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
    private void handleHinzufuegenButton() {
        // Implementiere die Logik zum Hinzufügen eines Teilnehmers
        showErrorAlert("Methode handleHinzufuegenButton muss implementiert werden");
    }

    @FXML
    private void handleAendernButton() {
        // Implementiere die Logik zum Ändern eines Teilnehmers
        showErrorAlert("Methode handleAendernButton muss implementiert werden");
    }

    @FXML
    private void handleLoeschenButton() {
        // Implementiere die Logik zum Löschen eines Teilnehmers
        showErrorAlert("Methode handleLoeschenButton muss implementiert werden");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
