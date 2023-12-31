package controller;

import core.model.Ausleihvorgang;
import core.model.SharingStandort;
import core.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import core.model.Fahrzeug;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;


public class FahrzeugController {

    @FXML
    private Label fahrzeugUebersichtLabel;

    @FXML
    private TableView<Fahrzeug> fahrzeugTableView;

    @FXML
    private TableColumn<Fahrzeug, Long> fahrzeugIdColumn;

    @FXML
    private TableColumn<Fahrzeug, String> herstellerColumn;

    @FXML
    private TableColumn<Fahrzeug, String> modellColumn;

    @FXML
    private TableColumn<Fahrzeug, String> ausstattungColumn;

    @FXML
    private TableColumn<Fahrzeug, Integer> leistungColumn;

    @FXML
    private TableColumn<Fahrzeug, String> kraftstoffartColumn;

    @FXML
    private TableColumn<Fahrzeug, Integer> baujahrColumn;

    @FXML
    private TableColumn<Fahrzeug, String> getriebeColumn;

    @FXML
    private TableColumn<Fahrzeug, Integer> kilometerstandColumn;

    @FXML
    private TableColumn<Fahrzeug, Integer> sitzplaetzeColumn;

    @FXML
    private TableColumn<Fahrzeug, SharingStandort> sharingStandortColumn;

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
    private ComboBox<Long> fahrzeugIdComboBox;

    private IFahrzeugService<Fahrzeug> fahrzeugService = new FahrzeugService();

    private ISharingStandortService sharingStandortService = new SharingStandortService();

    public static FahrzeugController getInstance() {
        return new FahrzeugController();
    }

    public AusleihvorgangService ausleihvorgangService = new AusleihvorgangService();

    @FXML
    private void initialize() {
        initFahrzeugTableView();
        initFahrzeugIdComboBox();
    }

    public void createAndSaveTestFahrzeuge() {
        //Testdaten hinzufügen
        Fahrzeug fahrzeug1 = new Fahrzeug("VW", "Golf", "Klimaanlage, Navi", 85, "Benzin", 2018, 10000, "Automatik", 5, (SharingStandort) sharingStandortService.find(1), false);
        Fahrzeug fahrzeug2 = new Fahrzeug("VW", "Polo", "Klimaanlage, Navi", 85, "Benzin", 2018, 10000, "Automatik", 5, (SharingStandort) sharingStandortService.find(2), false);

        //Testdaten speichern
        fahrzeugService.save(fahrzeug1);
        fahrzeugService.save(fahrzeug2);
    }

    protected void initFahrzeugTableView() {
        fahrzeugIdColumn.setCellValueFactory(new PropertyValueFactory<>("fahrzeugId"));
        herstellerColumn.setCellValueFactory(new PropertyValueFactory<>("hersteller"));
        modellColumn.setCellValueFactory(new PropertyValueFactory<>("modell"));
        ausstattungColumn.setCellValueFactory(new PropertyValueFactory<>("ausstattung"));
        baujahrColumn.setCellValueFactory(new PropertyValueFactory<>("baujahr"));
        getriebeColumn.setCellValueFactory(new PropertyValueFactory<>("getriebe"));
        kilometerstandColumn.setCellValueFactory(new PropertyValueFactory<>("kilometerstand"));
        kraftstoffartColumn.setCellValueFactory(new PropertyValueFactory<>("kraftstoffart"));
        leistungColumn.setCellValueFactory(new PropertyValueFactory<>("leistungKw"));
        sitzplaetzeColumn.setCellValueFactory(new PropertyValueFactory<>("sitzplaetze"));
        sharingStandortColumn.setCellValueFactory(new PropertyValueFactory<>("sharingStandort"));

        sharingStandortColumn.setCellFactory(column -> {
            return new TableCell<Fahrzeug, SharingStandort>() {
                @Override
                protected void updateItem(SharingStandort standort, boolean empty) {
                    super.updateItem(standort, empty);

                    if (standort == null || empty) {
                        setText(null);
                    } else {
                        setText(standort.getStandortName());
                    }
                }
            };
        });

        //Fahrzeuge aus der Datenquelle laden
        ObservableList<Fahrzeug> fahrzeuge = FXCollections.observableArrayList(fahrzeugService.findAll());

        //Nur nicht gelöschte Fahrzeuge anzeigen
        fahrzeuge = FXCollections.observableArrayList(fahrzeuge.stream()
                .filter(fahrzeug -> !fahrzeug.getDeleted())
                .collect(Collectors.toList())
        );

        //Fahrzeuge in TableView laden
        fahrzeugTableView.setItems(fahrzeuge);

    }

    protected void initFahrzeugIdComboBox() {
        //Fahrzeug-IDs aus der Datenquelle laden
        ObservableList<Long> fahrzeugIds = FXCollections.observableArrayList(fahrzeugService.findAll().stream()
                .map(Fahrzeug::getFahrzeugId)
                .collect(Collectors.toList())
        );

        //Nur nicht gelöschte Fahrzeuge anzeigen
        fahrzeugIds = FXCollections.observableArrayList(fahrzeugIds.stream()
                .filter(fahrzeugId -> !fahrzeugService.find(fahrzeugId).getDeleted())
                .collect(Collectors.toList())
        );

        //Fahrzeug-IDs in ComboBox laden
        fahrzeugIdComboBox.setItems(fahrzeugIds);
    }

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
        // Prüfen ob Fahrzeug ausgewählt wurde
        if (fahrzeugIdComboBox.getValue() == null) {
            showAlert("Bitte wählen Sie ein Fahrzeug aus.");
            return;
        }

        // Fahrzeug aus der Datenquelle laden
        Fahrzeug fahrzeug = fahrzeugService.find(fahrzeugIdComboBox.getValue());

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/fahrzeugAendern.fxml"));
            Parent root = loader.load();

            FahrzeugAendernController controller = loader.getController();
            controller.setFahrzeugController(this);
            controller.setSelectedFahrzeug(fahrzeug);

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
        try {
            //Fahrzeug aus der Datenquelle laden
            Fahrzeug fahrzeug = fahrzeugService.find(fahrzeugIdComboBox.getValue());

            //Prüfen ob Fahrzeug noch in aktiven Ausleihen involviert ist
            if (isFahrzeugInAktivenAusleihen(fahrzeug)) {
                showAlert("Fahrzeug kann nicht gelöscht werden, da es noch in Ausleihe ist.");
                return;
            }


            //Fragen ob wirklich gelöscht werden soll
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Fahrzeug löschen");
            alert.setHeaderText("Fahrzeug löschen");
            alert.setContentText("Möchten Sie das Fahrzeug wirklich löschen?");
            alert.showAndWait();

            if (alert.getResult() != ButtonType.OK) {
                return;
            }


            //Fahrzeug löschen
            fahrzeug.setDeleted(true);
            fahrzeugService.update(fahrzeug);
            showAlert("Fahrzeug erfolgreich gelöscht.");

            //TableView aktualisieren
            initFahrzeugTableView();

            //ComboBox aktualisieren
            initFahrzeugIdComboBox();
        } catch (Exception e) {
            showAlert("Fahrzeug konnte nicht gelöscht werden.");
        }
    }

    private boolean isFahrzeugInAktivenAusleihen(Fahrzeug fahrzeug) {
        List<Ausleihvorgang> ausleihvorgaenge = ausleihvorgangService.findAll();
        for (Ausleihvorgang ausleihvorgang : ausleihvorgaenge) {
            if (ausleihvorgang.getFahrzeug().getFahrzeugId() == fahrzeug.getFahrzeugId()) {
                //Prüfen ob der Ausleihvorgang abgeschlossen ist
                if (!ausleihvorgang.getAbgeschlossen()) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @FXML
    private void zurueckButtonClicked() {
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

    public IFahrzeugService<Fahrzeug> getFahrzeugService() {
        return fahrzeugService;
    }

    public ISharingStandortService getSharingStandortService() {
        return sharingStandortService;
    }

    public void aktualisierenButtonClicked(ActionEvent actionEvent) {
        initFahrzeugTableView();
    }
}
