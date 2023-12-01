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

public class FahrzeugAendernController {

    @FXML
    private TextField herstellerField;

    @FXML
    private TextField modellField;

    @FXML
    private TextField ausstattungField;

    @FXML
    private TextField leistungField;

    @FXML
    private TextField kilometerstandField;

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

    private Fahrzeug selectedFahrzeug;

    public void setSelectedFahrzeug(Fahrzeug selectedFahrzeug) {
        this.selectedFahrzeug = selectedFahrzeug;
        populateFields();
    }

    @FXML
    void initialize() {
        setFahrzeugController(FahrzeugController.getInstance());
        if (fahrzeugController != null) {
            initSharingComboBox();
            initGetriebeComboBox();
            initKraftstoffComboBox();
        } else {
            showAlert("Fehler beim Initialisieren des FahrzeugControllers.");
        }
    }

    public void setFahrzeugController(FahrzeugController instance) {
        this.fahrzeugController = instance;
    }

    private void initKraftstoffComboBox() {
        ObservableList<String> kraftstoffe = FXCollections.observableArrayList("Benzin", "Diesel", "Elektro", "Hybrid/Diesel", "Hybrid/Benzin");
        kraftstoffField.setItems(kraftstoffe);
    }

    private void initGetriebeComboBox() {
        ObservableList<String> getriebe = FXCollections.observableArrayList("Automatik", "Manuell");
        getriebeField.setItems(getriebe);
    }

    private void initSharingComboBox() {
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
                return null;
            }
        });

        sharingComboBox.setItems(sharingStandorte);
    }

    private void populateFields() {
        if (selectedFahrzeug != null) {
            herstellerField.setText(selectedFahrzeug.getHersteller());
            modellField.setText(selectedFahrzeug.getModell());
            ausstattungField.setText(selectedFahrzeug.getAusstattung());
            leistungField.setText(String.valueOf(selectedFahrzeug.getLeistungKw()));
            kilometerstandField.setText(String.valueOf(selectedFahrzeug.getKilometerstand()));
            kraftstoffField.setValue(selectedFahrzeug.getKraftstoffart());
            baujahrField.setText(String.valueOf(selectedFahrzeug.getBaujahr()));
            getriebeField.setValue(selectedFahrzeug.getGetriebe());
            sitzplaetzeField.setText(String.valueOf(selectedFahrzeug.getSitzplaetze()));
            sharingComboBox.setValue(selectedFahrzeug.getSharingStandort());
        }
    }

    @FXML
    private void speichernButtonClicked() {
        if (selectedFahrzeug != null) {
            String hersteller = herstellerField.getText();
            String modell = modellField.getText();
            String ausstattung = ausstattungField.getText();
            String leistung = leistungField.getText();
            String kilometerstand = kilometerstandField.getText();
            String kraftstoff = kraftstoffField.getValue();
            String baujahr = baujahrField.getText();
            String getriebe = getriebeField.getValue();
            String sitzplaetze = sitzplaetzeField.getText();
            SharingStandort sharingStandort = sharingComboBox.getValue();

            if (hersteller.isEmpty() || modell.isEmpty() || ausstattung.isEmpty() || leistung.isEmpty() ||
                    kilometerstand.isEmpty() || kraftstoff == null || kraftstoff.isEmpty() ||
                    baujahr.isEmpty() || getriebe == null || getriebe.isEmpty() || sitzplaetze.isEmpty() ||
                    sharingStandort == null) {
                showAlert("Bitte füllen Sie alle Felder aus.");
            } else {
                try {
                    showAlert("Fahrzeug erfolgreich aktualisiert.");

                    //Objekt aktualisieren
                    selectedFahrzeug.setHersteller(hersteller);
                    selectedFahrzeug.setModell(modell);
                    selectedFahrzeug.setAusstattung(ausstattung);
                    selectedFahrzeug.setLeistungKw(Integer.parseInt(leistung));
                    selectedFahrzeug.setKilometerstand(Integer.parseInt(kilometerstand));
                    selectedFahrzeug.setKraftstoffart(kraftstoff);
                    selectedFahrzeug.setBaujahr(Integer.parseInt(baujahr));
                    selectedFahrzeug.setGetriebe(getriebe);
                    selectedFahrzeug.setSitzplaetze(Integer.parseInt(sitzplaetze));
                    selectedFahrzeug.setSharingStandort(sharingStandort);

                    //Objekt speichern
                    fahrzeugController.getFahrzeugService().update(selectedFahrzeug);

                    clearFields();
                } catch (Exception e) {
                    showAlert("Fahrzeug konnte nicht aktualisiert werden.");
                }

                //TableView aktualisieren
                fahrzeugController.initFahrzeugTableView();

                //Stage schließen
                Stage stage = (Stage) speichernButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    @FXML
    private void zurueckButtonClicked() {
        Stage stage = (Stage) zurueckButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String message) {
        System.out.println(message);
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
}
