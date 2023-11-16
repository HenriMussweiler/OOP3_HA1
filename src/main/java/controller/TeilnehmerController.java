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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private TableView<Teilnehmer> teilnehmerTableView;

    @FXML
    private TableColumn<Teilnehmer, Long> teilnehmerIdColumn;

    @FXML
    private TableColumn<Teilnehmer, String> nameColumn;

    @FXML
    private TableColumn<Teilnehmer, String> vornameColumn;

    @FXML
    private TableColumn<Teilnehmer, String> strasseColumn;

    @FXML
    private TableColumn<Teilnehmer, String> hausnummerColumn;

    @FXML
    private TableColumn<Teilnehmer, String> plzColumn;

    @FXML
    private TableColumn<Teilnehmer, String> ortColumn;

    @FXML
    private TableColumn<Teilnehmer, String> ibanColumn;

    @FXML
    private TableColumn<Teilnehmer, String> mailColumn;

    @FXML
    private TableColumn<Teilnehmer, String> telefonColumn;

    @FXML
    private ComboBox<Long> teilnehmerIdComboBox;

    @FXML
    private Button aendernButton;

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

        // Lade den ausgewählten Teilnehmer aus der Datenquelle
        Teilnehmer teilnehmer = teilnehmerService.find(teilnehmerIdComboBox.getValue());

        // Öffne das Fenster zum Ändern des Teilnehmers
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/teilnehmerAendern.fxml"));
            Parent root = loader.load();

            TeilnehmerAendernController controller = loader.getController();
            controller.setTeilnehmerController(this);
            controller.setSelectedTeilnehmer(teilnehmer);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(aendernButton.getScene().getWindow());

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        initTeilnehmerTableView();
        initTeilnehmerIdComboBox();
    }

    private void initTeilnehmerTableView() {
        // Hier kannst du die Logik für die Anzeige der Fahrzeuge implementieren

        // Erstelle eine leere Liste für die Teilnehmer
        ObservableList<Teilnehmer> teilnehmer = FXCollections.observableArrayList();

        // Lade die Teilnehmer aus der Datenquelle
        teilnehmer.addAll(teilnehmerService.findAll());

        // Füge die Teilnehmer der TableView hinzu
        teilnehmerTableView.setItems(teilnehmer);

        // Erstelle die Spalten und verknüpfe sie mit den Attributen der Teilnehmer
        teilnehmerIdColumn.setCellValueFactory(new PropertyValueFactory<>("teilnehmerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        vornameColumn.setCellValueFactory(new PropertyValueFactory<>("vorname"));
        strasseColumn.setCellValueFactory(new PropertyValueFactory<>("strasse"));
        hausnummerColumn.setCellValueFactory(new PropertyValueFactory<>("hausnummer"));
        plzColumn.setCellValueFactory(new PropertyValueFactory<>("postleitzahl"));
        ortColumn.setCellValueFactory(new PropertyValueFactory<>("ort"));
        ibanColumn.setCellValueFactory(new PropertyValueFactory<>("iban"));
        mailColumn.setCellValueFactory(new PropertyValueFactory<>("mail"));
        telefonColumn.setCellValueFactory(new PropertyValueFactory<>("telefon"));
    }

    private void initTeilnehmerIdComboBox() {
        ObservableList<Long> teilnehmerIds = FXCollections.observableArrayList(teilnehmerService.findAll().stream()
                .map(Teilnehmer::getTeilnehmerId)
                .collect(Collectors.toList())
        );
        teilnehmerIdComboBox.setItems(teilnehmerIds);
    }

    public void updateTeilnehmerTableView() {
        // Lade die Teilnehmer erneut aus der Datenquelle
        ObservableList<Teilnehmer> teilnehmer = FXCollections.observableArrayList();
        teilnehmer.addAll(teilnehmerService.findAll());

        // Füge die aktualisierten Teilnehmer der TableView hinzu
        teilnehmerTableView.setItems(teilnehmer);
    }

}
