package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class FahrzeugController {

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private Button hinzufuegenButton;

    @FXML
    private Button aendernButton;

    @FXML
    private Button loeschenButton;

    @FXML
    private void handleHinzufuegenButton() {
        showAlert("Fahrzeug hinzufügen: " + herstellerField.getText() + " " + modellField.getText());
        // Hier könntest du die Logik für das Hinzufügen eines Fahrzeugs implementieren
    }

    @FXML
    private void handleAendernButton() {
        showAlert("Fahrzeug ändern: " + herstellerField.getText() + " " + modellField.getText());
        // Hier könntest du die Logik für das Ändern eines Fahrzeugs implementieren
    }

    @FXML
    private void handleLoeschenButton() {
        showAlert("Fahrzeug löschen: " + herstellerField.getText() + " " + modellField.getText());
        // Hier könntest du die Logik für das Löschen eines Fahrzeugs implementieren
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
