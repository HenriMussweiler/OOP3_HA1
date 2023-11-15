package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeilnehmerErstellenController {
    public void setTeilnehmerController(TeilnehmerController teilnehmerController) {
    }

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

    @FXML
    private void speichernButtonClicked() {
        // Implementiere die Logik für das Speichern des Teilnehmers
    }

    @FXML
    private void zurueckButtonClicked() {
        // Implementiere die Logik für den Zurück-Button
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }
}
