package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class AusleihvorgangController {

    @FXML
    private ComboBox<String> teilnehmerComboBox;

    @FXML
    private ComboBox<String> fahrzeugComboBox;

    @FXML
    private DatePicker startdatumPicker;

    @FXML
    private DatePicker enddatumPicker;

    @FXML
    private void handleReservierenButton() {
        // Implementiere die Logik zum Reservieren eines Fahrzeugs
        showErrorAlert("Methode handleReservierenButton muss implementiert werden");
    }

    @FXML
    private void handleBeendenButton() {
        // Implementiere die Logik zum Beenden eines Ausleihvorgangs
        showErrorAlert("Methode handleBeendenButton muss implementiert werden");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
