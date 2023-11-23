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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class AusleihvorgangController {

    @FXML
    protected TableView<Ausleihvorgang> ausleihvorgangTableView;
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
    private AusleihvorgangService ausleihvorgangService;

    @FXML
    protected void initialize() {
        //Testdaten erstellen
        //createTestData();

        //TableView anzeigen
        ausleihvorgangService = new AusleihvorgangService();
        initTableView();

        //ComboBoxen befüllen
        initComboBoxes();

    }

    public void createTestData() {
        ausleihvorgangService = new AusleihvorgangService();

        //Testdaten erstellen
        Ausleihvorgang ausleihvorgang1 = new Ausleihvorgang(fahrzeugService.find(6), teilnehmerService.find(4), LocalDateTime.parse("2023-11-02T00:00:00"), LocalDateTime.parse("2023-11-06T00:00:00"), false, 0, null);
        Ausleihvorgang ausleihvorgang2 = new Ausleihvorgang(fahrzeugService.find(7), teilnehmerService.find(4), LocalDateTime.parse("2023-11-07T00:00:00"), LocalDateTime.parse("2023-11-13T00:00:00"), false, 0, null);
        Ausleihvorgang ausleihvorgang3 = new Ausleihvorgang(fahrzeugService.find(6), teilnehmerService.find(5), LocalDateTime.parse("2023-11-01T00:00:00"), LocalDateTime.parse("2023-11-02T00:00:00"), false, 0, null);

        //Testdaten in der Datenbank speichern
        ausleihvorgangService.save(ausleihvorgang1);
        ausleihvorgangService.save(ausleihvorgang2);
        ausleihvorgangService.save(ausleihvorgang3);
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

        //Ausleihvorgänge filtern, die bereits abgeschlossen sind
        ausleihvorgang.removeIf(ausleihvorgang1 -> ausleihvorgangService.find(ausleihvorgang1).getAbgeschlossen());

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

    protected void initTableView() {
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

        //Tabelle leeren
        ausleihvorgangTableView.getItems().clear();

        //TableView mit Daten befüllen
        ObservableList<Ausleihvorgang> ausleihvorgang = FXCollections.observableArrayList(ausleihvorgangService.findAll());

        //Prüfen welche Ausleihvorgänge abgeschlossen sind und diese aus der Liste entfernen
        //Wenn abgeschlossen = true, dann wird das Objekt aus der Liste entfernt
        ausleihvorgang.removeIf(Ausleihvorgang::getAbgeschlossen);

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
        initialize();
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

        //Prüfen ob das Fahrzeug bereits ausgeliehen ist
        if (ausleihvorgangService.findAll().stream()
                .filter(ausleihvorgang -> ausleihvorgang.getFahrzeug().getModell().equals(fahrzeug))
                .anyMatch(ausleihvorgang -> ausleihvorgang.getEnddatum().isAfter(startdatumPicker.getValue().atStartOfDay()) && ausleihvorgang.getStartdatum().isBefore(enddatumPicker.getValue().atStartOfDay()))) {
            showErrorAlert("Das Fahrzeug ist in diesem Zeitraum bereits ausgeliehen.");
            return;
        }

        //Prüfen ob Startdatum vor dem Enddatum liegt
        if (startdatumPicker.getValue().isAfter(enddatumPicker.getValue())) {
            showErrorAlert("Das Startdatum muss vor dem Enddatum liegen.");
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
        // Prüfen ob ein Ausleihvorgang ausgewählt wurde
        if (stornierenComboBox.getValue() == null) {
            showErrorAlert("Bitte wählen Sie einen Ausleihvorgang aus.");
            return;
        }

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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
