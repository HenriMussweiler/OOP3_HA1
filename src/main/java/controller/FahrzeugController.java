package controller;

import core.service.FahrzeugService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import core.model.Fahrzeug;
import core.service.IFahrzeugService;
import javafx.stage.Stage;

import java.io.IOException;

public class FahrzeugController {

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private TextField kennzeichenField;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    private IFahrzeugService<Fahrzeug> fahrzeugService = new FahrzeugService();

    @FXML
    private void initialize() {
        // Initialisierung, wenn nötig
    }

    @FXML
    private void handleSpeichernButtonClick() {
        String hersteller = herstellerField.getText();
        String modell = modellField.getText();
        String kennzeichen = kennzeichenField.getText();

        if (hersteller.isEmpty() || modell.isEmpty() || kennzeichen.isEmpty()) {
            showAlert("Bitte füllen Sie alle Felder aus.");
        } else {
            try {
                Fahrzeug neuesFahrzeug = new Fahrzeug(hersteller, modell);
                fahrzeugService.save(neuesFahrzeug);
                showAlert("Fahrzeug erfolgreich angelegt.");
                clearFields();
            } catch (Exception e) {
                showAlert("Fahrzeug konnte nicht angelegt werden.");
            }
        }
    }

    @FXML
    private void handleAendernButtonClick() {
        // Hier kannst du die Logik für die Änderung eines Fahrzeugs implementieren
    }

    @FXML
    private void handleLoeschenButtonClick() {
        // Hier kannst du die Logik für das Löschen eines Fahrzeugs implementieren
    }

    @FXML
    private void handleZurueckButtonClick() {
        // Hier kannst du die Logik für den "Zurück"-Button implementieren
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/main.fxml"));
            Parent root = loader.load();

            MainController controller = loader.getController();
            controller.setPrimaryStage((Stage) zurueckButton.getScene().getWindow());

            Scene scene = new Scene(root);
            Stage stage = (Stage) zurueckButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        herstellerField.clear();
        modellField.clear();
        kennzeichenField.clear();
    }
}
