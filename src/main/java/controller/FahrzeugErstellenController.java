package controller;

import core.model.Fahrzeug;
import core.model.SharingStandort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class FahrzeugErstellenController {

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private TextField ausstattungField;

    @FXML
    private TextField leistungField;

    @FXML
    private TextField kraftstoffField;

    @FXML
    private TextField baujahrField;

    @FXML
    private TextField getriebeField;

    @FXML
    private TextField sitzplaetzeField;

    @FXML
    private ComboBox<String> sharingComboBox;

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
    }

    private void initSharingComboBox() {
        // Hier kannst du die Logik für die Anzeige der Sharing-Standorte implementieren
        List<SharingStandort> sharingStandortList = fahrzeugController.getSharingStandortService().findAll();

        ObservableList<String> sharingStandorte = sharingStandortList.stream()
                .map(SharingStandort::getStandortName)
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

        sharingComboBox.setItems(sharingStandorte);
    }



    @FXML
    private void speichernButtonClicked() {
        String hersteller = herstellerField.getText();
        String modell = modellField.getText();
        String ausstattung = ausstattungField.getText();
        String leistung = leistungField.getText();
        String kraftstoff = kraftstoffField.getText();
        String baujahr = baujahrField.getText();
        String getriebe = getriebeField.getText();
        String sitzplaetze = sitzplaetzeField.getText();
        String sharingStandort = sharingComboBox.getValue();

        if (hersteller.isEmpty() || modell.isEmpty() || ausstattung.isEmpty() || leistung.isEmpty() ||
                kraftstoff.isEmpty() || baujahr.isEmpty() || getriebe.isEmpty() || sitzplaetze.isEmpty() ||
                sharingStandort == null || sharingStandort.isEmpty()) {
            showAlert("Bitte füllen Sie alle Felder aus.");
        } else {
            try {
                // Hier könntest du die Logik zum Speichern des neuen Fahrzeugs implementieren
                showAlert("Fahrzeug erfolgreich angelegt.");
                //Objekt anlegen
                Fahrzeug newFahrzeug = new Fahrzeug(hersteller, modell, ausstattung, Integer.parseInt(leistung), kraftstoff, Integer.parseInt(baujahr), Integer.parseInt(getriebe), Integer.parseInt(sitzplaetze), sharingStandort);
                //Objekt speichern
                fahrzeugController.getFahrzeugService().save(newFahrzeug);

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
        kraftstoffField.clear();
        baujahrField.clear();
        getriebeField.clear();
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
