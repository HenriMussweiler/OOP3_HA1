package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.model.SharingStandort;
import core.service.SharingStandortService;
import javafx.scene.control.Alert;


import java.io.IOException;

public class SharingStandortController {

    @FXML
    private TextField nameField;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private void initialize() {
    }

    public void createTestData() {
        //Testdaten hinzufügen
        SharingStandort sharingStandort1 = new SharingStandort("Osnabrück");
        SharingStandort sharingStandort2 = new SharingStandort("Münster");
        SharingStandort sharingStandort3 = new SharingStandort("Bielefeld");

        //Testdaten speichern
        SharingStandortService sharingStandortService = new SharingStandortService();
        sharingStandortService.save(sharingStandort1);
        sharingStandortService.save(sharingStandort2);
        sharingStandortService.save(sharingStandort3);
    }

    @FXML
    void speichernButtonClicked() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            showAlert("Bitte geben Sie den Namen des Standorts ein.");
        } else {
            try {
                // Objekt anlegen
                SharingStandort newSharingStandort = new SharingStandort(name);

                // Objekt speichern
                SharingStandortService sharingStandortService = new SharingStandortService();
                sharingStandortService.save(newSharingStandort);

                // Erfolgsmeldung anzeigen
                showAlert("Sharing-Standort erfolgreich angelegt.");

                nameField.clear();
            } catch (Exception e) {
                showAlert("Sharing-Standort konnte nicht angelegt werden.");
            }
        }
    }

    @FXML
    void zurueckButtonClicked() {
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
}
