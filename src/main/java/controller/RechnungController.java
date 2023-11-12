package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class RechnungController {

    @FXML
    private ComboBox<String> teilnehmerComboBox;

    @FXML
    private DatePicker startdatumPicker;

    @FXML
    private DatePicker enddatumPicker;

    @FXML
    private void handleGeneriereRechnungButton() {
        // Implementiere die Logik zur Generierung einer Rechnung
        showErrorAlert("Methode handleGeneriereRechnungButton muss implementiert werden");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
