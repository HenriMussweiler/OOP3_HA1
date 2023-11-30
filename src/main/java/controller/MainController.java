package controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import java.io.IOException;

public class MainController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void fahrzeugButtonClicked() {
        loadView("/ui/fahrzeug.fxml", "Fahrzeugverwaltung");
    }

    @FXML
    private void teilnehmerButtonClicked() {
        loadView("/ui/teilnehmer.fxml", "Teilnehmerverwaltung");
    }

    @FXML
    private void ausleihvorgangButtonClicked() {
        loadView("/ui/ausleihvorgang.fxml", "Ausleihvorgangverwaltung");
    }

    @FXML
    private void sharingButtonClicked() {
        loadView("/ui/sharingStandort.fxml", "Sharingverwaltung");
    }

    @FXML
    private void rechnungButtonClicked() {
        loadView("/ui/rechnung.fxml", "Rechnungsverwaltung");
    }

    @FXML
    void initialize() {
    }

    private void loadView(String resource, String title) {
        try {
            // Lade die FXML-Datei
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent root = loader.load();

            // Erstelle eine neue Szene
            Scene scene = new Scene(root);

            // Setze die neue Szene auf die aktuelle Bühne (primaryStage)
            primaryStage.setScene(scene);
            primaryStage.setTitle(title);
            primaryStage.sizeToScene();

        } catch (IOException e) {
            showErrorAlert("Fehler beim Laden der Ansicht");
            e.printStackTrace();
        }
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //Testdaten holen
    public void createTestData() {
        SharingStandortController sharingStandortController = new SharingStandortController();
        sharingStandortController.createTestData();

        TeilnehmerController teilnehmerController = new TeilnehmerController();
        teilnehmerController.createAndSaveTestdaten();

        FahrzeugController fahrzeugController = new FahrzeugController();
        fahrzeugController.createAndSaveTestFahrzeuge();

        AusleihvorgangController ausleihvorgangController = new AusleihvorgangController();
        ausleihvorgangController.createTestData();
    }
}
