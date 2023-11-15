package controller;

import core.model.Teilnehmer;
import core.service.ITeilnehmerService;
import core.service.TeilnehmerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

public class TeilnehmerController {

    @FXML
    private Button hinzufuegenButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private ListView<Teilnehmer> teilnehmerListView;

    @FXML
    private ComboBox<Long> teilnehmerIdComboBox;

    private ITeilnehmerService<Teilnehmer> teilnehmerService = new TeilnehmerService();

    @FXML
    private void hinzufuegenButtonClicked() {
        // Implementiere die Logik zum Hinzufügen eines Teilnehmers
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/teilnehmerErstellen.fxml"));
            Parent root = loader.load();

            TeilnehmerErstellenController controller = loader.getController();
            controller.setTeilnehmerController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(hinzufuegenButton.getScene().getWindow());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void aendernButtonClicked() {
        // Implementiere die Logik zum Ändern eines Teilnehmers
        showErrorAlert("Methode handleAendernButton muss implementiert werden");
    }

    @FXML
    private void loeschenButtonClicked() {
        // Implementiere die Logik zum Löschen eines Teilnehmers
        showErrorAlert("Methode handleLoeschenButton muss implementiert werden");
    }

    @FXML
    private void zurueckButtonClicked() {
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void initialize() {
        // Initialisierung, wenn nötig
        initTeilnehmerListView();
        initTeilnehmerIdComboBox();
    }

    private void initTeilnehmerListView() {
        // Hier kannst du die Logik für die Anzeige der Fahrzeuge implementieren
        ObservableList<Teilnehmer> teilnehmer = FXCollections.observableArrayList(teilnehmerService.findAll());
        teilnehmerListView.setItems(teilnehmer);
    }

    private void initTeilnehmerIdComboBox() {
        ObservableList<Long> teilnehmerIds = FXCollections.observableArrayList(teilnehmerService.findAll().stream()
                .map(Teilnehmer::getTeilnehmerId)
                .collect(Collectors.toList())
        );
        teilnehmerIdComboBox.setItems(teilnehmerIds);
    }
}
