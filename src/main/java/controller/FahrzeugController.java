package controller;

import core.model.SharingStandort;
import core.service.FahrzeugService;
import core.service.ISharingStandortService;
import core.service.SharingStandortService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import core.model.Fahrzeug;
import core.service.IFahrzeugService;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;


public class FahrzeugController {

    @FXML
    private Label fahrzeugUebersichtLabel;

    @FXML
    private ListView<Fahrzeug> fahrzeugListView;

    @FXML
    private Button hinzufuegenButton;

    @FXML
    private Button aendernButton;

    @FXML
    private Button loeschenButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private Label fahrzeugIdLabel;

    @FXML
    private ComboBox<Long> fahrzeugId;

    private IFahrzeugService<Fahrzeug> fahrzeugService = new FahrzeugService();

    private ISharingStandortService sharingStandortService = new SharingStandortService();

    public static FahrzeugController getInstance() {
        return new FahrzeugController();
    }

    @FXML
    private void initialize() {
        // Initialisierung, wenn nötig
        initFahrzeugListView();
        initFahrzeugIdComboBox();
    }

    private void initFahrzeugListView() {
        // Hier kannst du die Logik für die Anzeige der Fahrzeuge implementieren
        ObservableList<Fahrzeug> fahrzeuge = FXCollections.observableArrayList(fahrzeugService.findAll());
        fahrzeugListView.setItems(fahrzeuge);
    }

    private void initFahrzeugIdComboBox() {
        ObservableList<Long> fahrzeugIds = FXCollections.observableArrayList(fahrzeugService.findAll().stream()
                .map(Fahrzeug::getFahrzeugId)
                .collect(Collectors.toList())
        );
        fahrzeugId.setItems(fahrzeugIds);
    }

//    @FXML
//    private void hinzufuegenButtonClicked() {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fahrzeugErstellen.fxml"));
//            Parent root = loader.load();
//
//            FahrzeugErstellenController controller = loader.getController();
//            controller.setFahrzeugController(this);
//
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void hinzufuegenButtonClicked() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fahrzeugErstellen.fxml"));
            Parent root = loader.load();

            FahrzeugErstellenController controller = loader.getController();
            controller.setFahrzeugController(this);

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
        // Hier kannst du die Logik für die Änderung eines Fahrzeugs implementieren
    }

    @FXML
    private void loeschenButtonClicked() {
        // Hier kannst du die Logik für das Löschen eines Fahrzeugs implementieren
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
//        herstellerField.clear();
//        modellField.clear();
//        kennzeichenField.clear();
    }

    public IFahrzeugService<Fahrzeug> getFahrzeugService() {
        return fahrzeugService;
    }

    public ISharingStandortService getSharingStandortService() {
        return sharingStandortService;
    }

}
