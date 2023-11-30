package controller;

import core.model.Fahrzeug;
import core.model.SharingStandort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.List;

public class FahrzeugErstellenController {

    @FXML
    public TextField kilometerstandField;

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private TextField ausstattungField;

    @FXML
    private TextField leistungField;

    @FXML
    private ComboBox<String> kraftstoffField;

    @FXML
    private TextField baujahrField;

    @FXML
    private ComboBox<String> getriebeField;

    @FXML
    private TextField sitzplaetzeField;

    @FXML
    private ComboBox<SharingStandort> sharingComboBox;

    @FXML
    private Button speichernButton;

    @FXML
    private Button zurueckButton;

    private FahrzeugController fahrzeugController;

    @FXML
    void initialize() {
        setFahrzeugController(FahrzeugController.getInstance());
        if (fahrzeugController != null) {
            initSharingComboBox();
        } else {
            // Handle null fahrzeugController, z.B. durch eine Fehlermeldung oder ein Log-Statement
            showAlert("Fehler beim Initialisieren des FahrzeugControllers.");
        }
        initGetriebeComboBox();
        initKraftstoffComboBox();
    }

    private void initKraftstoffComboBox() {
        // Hier kannst du die Logik für die Anzeige der Kraftstoffe implementieren
        ObservableList<String> kraftstoffe = FXCollections.observableArrayList("Benzin", "Diesel", "Elektro", "Hybrid/Diesel", "Hybrid/Benzin");
        kraftstoffField.setItems(kraftstoffe);
    }

    private void initGetriebeComboBox() {
        // Hier kannst du die Logik für die Anzeige der Getriebe implementieren
        ObservableList<String> getriebe = FXCollections.observableArrayList("Automatik", "Manuell");
        getriebeField.setItems(getriebe);
    }

    private void initSharingComboBox() {
        // Hier kannst du die Logik für die Anzeige der Sharing-Standorte implementieren
        List<SharingStandort> sharingStandortList = fahrzeugController.getSharingStandortService().findAll();

        ObservableList<SharingStandort> sharingStandorte = FXCollections.observableArrayList(sharingStandortList);

        sharingComboBox.setCellFactory(new Callback<>() {
            @Override
            public ListCell<SharingStandort> call(ListView<SharingStandort> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(SharingStandort sharingStandort, boolean empty) {
                        super.updateItem(sharingStandort, empty);

                        if (sharingStandort == null || empty) {
                            setText(null);
                        } else {
                            setText(sharingStandort.getStandortName());
                        }
                    }
                };
            }
        });

        sharingComboBox.setConverter(new StringConverter<SharingStandort>() {
            @Override
            public String toString(SharingStandort object) {
                return object == null ? null : object.getStandortName();
            }

            @Override
            public SharingStandort fromString(String string) {
                // Du kannst hier die Umwandlung von String zu SharingStandort implementieren, falls notwendig
                return null;
            }
        });

        sharingComboBox.setItems(sharingStandorte);
    }




    @FXML
    private void speichernButtonClicked() {
        String hersteller = herstellerField.getText();
        String modell = modellField.getText();
        String ausstattung = ausstattungField.getText();
        String leistung = leistungField.getText();
        String kraftstoff = kraftstoffField.getValue();
        String baujahr = baujahrField.getText();
        String kilometerstand = kilometerstandField.getText();
        String getriebe = getriebeField.getValue();
        String sitzplaetze = sitzplaetzeField.getText();
        SharingStandort sharingStandort = sharingComboBox.getValue();

        if (hersteller.isEmpty() || modell.isEmpty() || ausstattung.isEmpty() || leistung.isEmpty() ||
                kraftstoff.isEmpty() || baujahr.isEmpty() || getriebe.isEmpty() || sitzplaetze.isEmpty() ||
                sharingStandort == null) {
            showAlert("Bitte füllen Sie alle Felder aus.");
        } else {
            try {
                //Objekt anlegen
                Fahrzeug newFahrzeug = new Fahrzeug(hersteller, modell, ausstattung, Integer.parseInt(leistung), kraftstoff, Integer.parseInt(baujahr), Integer.parseInt(kilometerstand), getriebe, Integer.parseInt(sitzplaetze), sharingStandort, false);
                //Objekt speichern
                fahrzeugController.getFahrzeugService().save(newFahrzeug);
                // Hier könntest du die Logik zum Speichern des neuen Fahrzeugs implementieren
                showAlert("Fahrzeug erfolgreich angelegt.");

                //TableView aktualisieren
                fahrzeugController.initFahrzeugTableView();

                //ComboBox aktualisieren
                fahrzeugController.initFahrzeugIdComboBox();

                //Felder leeren
                clearFields();
            } catch (Exception e) {
                showAlert("Fahrzeug konnte nicht angelegt werden.");
            }
        }
    }

    private void clearFields() {
        herstellerField.clear();
        modellField.clear();
        ausstattungField.clear();
        leistungField.clear();
        kilometerstandField.clear();
        kraftstoffField.getSelectionModel().clearSelection();
        baujahrField.clear();
        getriebeField.getSelectionModel().clearSelection();
        sitzplaetzeField.clear();
        sharingComboBox.getSelectionModel().clearSelection();
    }

    @FXML
    private void zurueckButtonClicked() {
        // Hier kannst du die Logik für den "Zurück"-Button implementieren
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void setFahrzeugController(FahrzeugController fahrzeugController) {
        this.fahrzeugController = fahrzeugController;
    }
}
