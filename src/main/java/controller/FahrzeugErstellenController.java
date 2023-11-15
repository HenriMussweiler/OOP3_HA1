package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FahrzeugErstellenController {

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private TextField ausstattungField;

    @FXML
    private TextField leistungField;

    @FXML
    private TextField kraftstoffField;

    @FXML
    private TextField baujahrField;

    @FXML
    private TextField getriebeField;

    @FXML
    private TextField sitzplaetzeField;

    @FXML
    private ComboBox<String> sharingComboBox;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    private FahrzeugController fahrzeugController;

    @FXML
    private void speichernButtonClicked() {
        String hersteller = herstellerField.getText();
        String modell = modellField.getText();
        String ausstattung = ausstattungField.getText();
        String leistung = leistungField.getText();
        String kraftstoff = kraftstoffField.getText();
        String baujahr = baujahrField.getText();
        String getriebe = getriebeField.getText();
        String sitzplaetze = sitzplaetzeField.getText();
        String sharingStandort = sharingComboBox.getValue();

        if (hersteller.isEmpty() || modell.isEmpty() || ausstattung.isEmpty() || leistung.isEmpty() ||
                kraftstoff.isEmpty() || baujahr.isEmpty() || getriebe.isEmpty() || sitzplaetze.isEmpty() ||
                sharingStandort == null || sharingStandort.isEmpty()) {
            showAlert("Bitte füllen Sie alle Felder aus.");
        } else {
            try {
                // Hier könntest du die Logik zum Speichern des neuen Fahrzeugs implementieren
                showAlert("Fahrzeug erfolgreich angelegt.");
                // Schließe das Fenster
                Stage stage = (Stage) herstellerField.getScene().getWindow();
                stage.close();
            } catch (Exception e) {
                showAlert("Fahrzeug konnte nicht angelegt werden.");
            }
        }
    }

    @FXML
    private void zurueckButtonClicked() {
        // Hier kannst du die Logik für den "Zurück"-Button implementieren
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setFahrzeugController(FahrzeugController fahrzeugController) {
        this.fahrzeugController = fahrzeugController;
    }
}
