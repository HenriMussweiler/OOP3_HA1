package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import core.model.SharingStandort;

import java.io.IOException;

public class SharingStandortController {

    @FXML
    private TextField nameField;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    @FXML
    void speichernButtonClicked() {
        // Hier implementiere die Logik, um den neuen Sharing-Standort zu speichern
        String name = nameField.getText();

        SharingStandort sharingStandort = new SharingStandort();
        // Hier rufe die Methode auf, um den Sharing-Standort zu speichern

        // Schließe das aktuelle Fenster
        Stage stage = (Stage) speichernButton.getScene().getWindow();
        stage.close();
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
}
