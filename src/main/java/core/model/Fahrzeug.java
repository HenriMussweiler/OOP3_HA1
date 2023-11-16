package core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fahrzeug")
public class Fahrzeug {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fahrzeug_id")
    private Long fahrzeugId;

    @Column(name = "hersteller")
    private String hersteller;

    @Column(name = "modell")
    private String modell;

    @Column(name = "ausstattung")
    private String ausstattung;

    @Column(name = "leistung_kw")
    private int leistungKw;

    @Column(name = "kraftstoffart")
    private String kraftstoffart;

    @Column(name = "baujahr")
    private int baujahr;

    @Column(name = "kilometerstand")
    private int kilometerstand;

    @Column(name = "getriebe")
    private String getriebe;

    @Column(name = "sitzplaetze")
    private int sitzplaetze;

    @ManyToOne
    @JoinColumn(name = "sharing_standort_id")
    private SharingStandort sharingStandort;

    // Konstruktor, Getter und Setter hier...

    public Fahrzeug() {
    }

    public Fahrzeug(String hersteller, String modell, String ausstattung, int leistungKw, String kraftstoffart, int baujahr, int kilometerstand, String getriebe, int sitzplaetze, SharingStandort sharingStandort) {
        this.hersteller = hersteller;
        this.modell = modell;
        this.ausstattung = ausstattung;
        this.leistungKw = leistungKw;
        this.kraftstoffart = kraftstoffart;
        this.baujahr = baujahr;
        this.kilometerstand = kilometerstand;
        this.getriebe = getriebe;
        this.sitzplaetze = sitzplaetze;
        this.sharingStandort = sharingStandort;
    }

    public Fahrzeug(String hersteller, String modell, String ausstattung, int leistungKw, String kraftstoff, int baujahr, int kilometerstand, int sitzplaetze, String sharingStandort) {
        this.hersteller = hersteller;
        this.modell = modell;
        this.ausstattung = ausstattung;
        this.leistungKw = leistungKw;
        this.kraftstoffart = kraftstoff;
        this.baujahr = baujahr;
        this.kilometerstand = kilometerstand;
        this.sitzplaetze = sitzplaetze;
        this.sharingStandort = new SharingStandort(sharingStandort);
    }

    public Long getFahrzeugId() {
        return fahrzeugId;
    }

    public void setFahrzeugId(Long fahrzeugId) {
        this.fahrzeugId = fahrzeugId;
    }

    public String getHersteller() {
        return hersteller;
    }

    public void setHersteller(String hersteller) {
        this.hersteller = hersteller;
    }

    public String getModell() {
        return modell;
    }

    public void setModell(String modell) {
        this.modell = modell;
    }

    public String getAusstattung() {
        return ausstattung;
    }

    public void setAusstattung(String ausstattung) {
        this.ausstattung = ausstattung;
    }

    public int getLeistungKw() {
        return leistungKw;
    }

    public void setLeistungKw(int leistungKw) {
        this.leistungKw = leistungKw;
    }

    public String getKraftstoffart() {
        return kraftstoffart;
    }

    public void setKraftstoffart(String kraftstoffart) {
        this.kraftstoffart = kraftstoffart;
    }

    public int getBaujahr() {
        return baujahr;
    }

    public void setBaujahr(int baujahr) {
        this.baujahr = baujahr;
    }

    public int getKilometerstand() {
        return kilometerstand;
    }

    public void setKilometerstand(int kilometerstand) {
        this.kilometerstand = kilometerstand;
    }

    public String getGetriebe() {
        return getriebe;
    }

    public void setGetriebe(String getriebe) {
        this.getriebe = getriebe;
    }

    public int getSitzplaetze() {
        return sitzplaetze;
    }

    public void setSitzplaetze(int sitzplaetze) {
        this.sitzplaetze = sitzplaetze;
    }

    public SharingStandort getSharingStandort() {
        return sharingStandort;
    }

    public void setSharingStandort(SharingStandort sharingStandort) {
        this.sharingStandort = sharingStandort;
    }

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
