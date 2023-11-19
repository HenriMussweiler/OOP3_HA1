package controller;

import core.model.Ausleihvorgang;
import core.model.Fahrzeug;
import core.model.Teilnehmer;
import core.service.FahrzeugService;
import core.service.TeilnehmerService;
import core.service.AusleihvorgangService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AusleihvorgangController {

    @FXML
    private TableView<Ausleihvorgang> ausleihvorgangTableView;
    @FXML
    public TableColumn<Ausleihvorgang, Long> ausleihvorgangIdColumn;

    @FXML
    public TableColumn<Ausleihvorgang, String> teilnehmerColumn;

    @FXML
    public TableColumn<Ausleihvorgang, String> fahrzeugColumn;

    @FXML
    public TableColumn<Ausleihvorgang, String> startdatumColumn;

    @FXML
    public TableColumn<Ausleihvorgang, String> enddatumColumn;

    @FXML
    private ComboBox<String> teilnehmerComboBox;

    @FXML
    private ComboBox<String> fahrzeugComboBox;

    @FXML
    private DatePicker startdatumPicker;

    @FXML
    private DatePicker enddatumPicker;

    @FXML
    private Button stornierenButton;

    @FXML
    private Button aktualisierenButton;

    @FXML
    private Button reservierenButton;

    @FXML
    private Button zurueckButton;

    @FXML
    private ComboBox<Long> stornierenComboBox;

    @FXML
    private Button aendernButton;

    private FahrzeugService fahrzeugService = new FahrzeugService();
    private TeilnehmerService teilnehmerService = new TeilnehmerService();
    private AusleihvorgangService ausleihvorgangService = new AusleihvorgangService();

    @FXML
    private void initialize() {
        //TableView anzeigen
        initTableView();

        //ComboBoxen befüllen
        initComboBoxes();

    }

    private void initComboBoxes() {
        initFahrzeugComboBox();
        initTeilnehmerComboBox();
        initStornierenComboBox();
    }

    private void initStornierenComboBox() {
        ObservableList<Long> ausleihvorgang = FXCollections.observableArrayList(ausleihvorgangService.findAll().stream()
                .map(Ausleihvorgang::getAusleihvorgangId)
                .toList()
        );
        stornierenComboBox.setItems(ausleihvorgang);
    }

    private void initTeilnehmerComboBox() {
        ObservableList<String> teilnehmer = FXCollections.observableArrayList(teilnehmerService.findAll().stream()
                .map(Teilnehmer::getName)
                .map(String::valueOf)
                .toList()
        );
        teilnehmerComboBox.setItems(teilnehmer);
    }

    private void initFahrzeugComboBox() {
        ObservableList<String> fahrzeug = FXCollections.observableArrayList(fahrzeugService.findAll().stream()
                .map(Fahrzeug::getModell)
                .toList()
        );
        fahrzeugComboBox.setItems(fahrzeug);
    }

    private void initTableView() {
        ausleihvorgangIdColumn.setCellValueFactory(new PropertyValueFactory<>("ausleihvorgangId"));

        teilnehmerColumn.setCellValueFactory(cellData -> {
            Ausleihvorgang ausleihvorgang = cellData.getValue();
            return new SimpleStringProperty(ausleihvorgang.getTeilnehmer().getName());
        });

        fahrzeugColumn.setCellValueFactory(cellData -> {
            Ausleihvorgang ausleihvorgang = cellData.getValue();
            return new SimpleStringProperty(ausleihvorgang.getFahrzeug().getModell());
        });

        startdatumColumn.setCellValueFactory(new PropertyValueFactory<>("startdatum"));
        enddatumColumn.setCellValueFactory(new PropertyValueFactory<>("enddatum"));

        ObservableList<Ausleihvorgang> ausleihvorgang = FXCollections.observableArrayList(ausleihvorgangService.findAll());
        ausleihvorgangTableView.setItems(ausleihvorgang);
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void stornierenButtonClicked(ActionEvent actionEvent) {
        Long ausleihvorgangId = stornierenComboBox.getValue();

        if (ausleihvorgangId == null) {
            showErrorAlert("Bitte wählen Sie einen Ausleihvorgang aus.");
            return;
        }

        //Ausleihvorgang-Objekt aus der Datenbank auslesen
        Ausleihvorgang ausleihvorgang = ausleihvorgangService.find(ausleihvorgangId);

        //Ausleihvorgang-Objekt aus der Datenbank löschen
        ausleihvorgangService.delete(ausleihvorgang.getAusleihvorgangId());

        //Zeige eine Erfolgsmeldung an
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ausleihvorgang storniert");
        alert.setHeaderText(null);
        alert.setContentText("Der Ausleihvorgang wurde erfolgreich storniert.");
        alert.showAndWait();

        //TableView aktualisieren
        ausleihvorgangTableView.getItems().remove(ausleihvorgang);

        //ComboBox aktualisieren
        initComboBoxes();
    }

    @FXML
    private void aktualisierenButtonClicked(ActionEvent actionEvent) {
        //TabkleView aktualisieren
        ausleihvorgangTableView.getItems().clear();
        ausleihvorgangTableView.getItems().addAll(ausleihvorgangService.findAll());

        //ComboBox aktualisieren
        initComboBoxes();
    }

    @FXML
    private void reservierenButtonClicked(ActionEvent actionEvent) {
        String teilnehmer = teilnehmerComboBox.getValue();
        String fahrzeug = fahrzeugComboBox.getValue();
        String startdatum = startdatumPicker.getValue().atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String enddatum = enddatumPicker.getValue().atStartOfDay().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        if (teilnehmer == null || fahrzeug == null || startdatum == null || enddatum == null) {
            showErrorAlert("Bitte füllen Sie alle Felder aus.");
            return;
        }

        Long teilnehmerId;
        Long fahrzeugId;

        //TeilnehmerId und FahrzeugId aus den Strings auslesen
        try {
            teilnehmerId = teilnehmerService.findAll().stream()
                    .filter(teilnehmer1 -> teilnehmer1.getName().equals(teilnehmer))
                    .map(Teilnehmer::getTeilnehmerId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Teilnehmer nicht gefunden"));

            fahrzeugId = fahrzeugService.findAll().stream()
                    .filter(fahrzeug1 -> fahrzeug1.getModell().equals(fahrzeug))
                    .map(Fahrzeug::getFahrzeugId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Fahrzeug nicht gefunden"));
        } catch (NoSuchElementException e) {
            showErrorAlert("Teilnehmer oder Fahrzeug nicht gefunden.");
            return;
        }

        //Fahrzeug- und Teilnehmer-Objekt aus der Datenbank auslesen
        Fahrzeug fahrzeug1 = fahrzeugService.find(fahrzeugId);
        Teilnehmer teilnehmer1 = teilnehmerService.find(teilnehmerId);

        //Erstelle ein neues Ausleihvorgang-Objekt
        Ausleihvorgang ausleihvorgang = new Ausleihvorgang(fahrzeug1, teilnehmer1, startdatum, enddatum);

        //Speichere das Ausleihvorgang-Objekt in der Datenbank
        ausleihvorgangService.save(ausleihvorgang);

        //Zeige eine Erfolgsmeldung an
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ausleihvorgang erstellt");
        alert.setHeaderText(null);
        alert.setContentText("Der Ausleihvorgang wurde erfolgreich erstellt.");
        alert.showAndWait();

        //Felder leeren
        teilnehmerComboBox.setValue(null);
        fahrzeugComboBox.setValue(null);
        startdatumPicker.setValue(null);
        enddatumPicker.setValue(null);

        //TableView aktualisieren
        ausleihvorgangTableView.getItems().add(ausleihvorgang);

        //ComboBox aktualisieren
        initComboBoxes();
    }

    @FXML
    private void zurueckButtonClicked(ActionEvent actionEvent) {
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

    public void aendernButtonClicked(ActionEvent actionEvent) {
        // Hole den ausgewählten Ausleihvorgang aus der ComboBox
        Long ausleihvorgangId = stornierenComboBox.getValue();
        if (ausleihvorgangId == null) {
            showErrorAlert("Bitte wählen Sie einen Ausleihvorgang aus.");
            return;
        }

        // Hole den ausgewählten Ausleihvorgang aus der Datenbank
        Ausleihvorgang ausleihvorgang = ausleihvorgangService.find(ausleihvorgangId);

        // Erstelle ein neues Fenster um den Ausleihvorgang zu bearbeiten
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ausleihvorgangAendern.fxml"));
            Parent root = loader.load();

            AusleihvorgangAendernController controller = loader.getController();
            controller.setAusleihvorgangController(this);
            controller.setAusleihvorgang(ausleihvorgang);
            controller.setSelectedAusleihvorgang(ausleihvorgang);


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
}
