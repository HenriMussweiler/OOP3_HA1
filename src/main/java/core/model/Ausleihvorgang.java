package core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ausleihvorgang")
public class Ausleihvorgang {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ausleihvorgang_generator")
    @Column(name = "ausleihvorgang_id")
    private Long ausleihvorgangId;

    @ManyToOne
    @JoinColumn(name = "fahrzeug_id")
    private Fahrzeug fahrzeug;

    @ManyToOne
    @JoinColumn(name = "teilnehmer_id")
    private Teilnehmer teilnehmer;

    @Column(name = "startdatum")
    private LocalDateTime startdatum;

    @Column(name = "enddatum")
    private LocalDateTime enddatum;

    @Column(name = "abgeschlossen")
    private boolean abgeschlossen;

    @Column(name = "storniert")
    private boolean storniert;

    @Column(name = "gefahrene_kilometer")
    private int gefahreneKilometer;

    @ManyToOne
    @JoinColumn(name = "rechnung_id")
    private Rechnung rechnung;

    public Ausleihvorgang(Fahrzeug fahrzeug, Teilnehmer teilnehmer, LocalDateTime startdatum, LocalDateTime enddatum, boolean abgeschlossen, boolean storniert, int gefahreneKilometer, Rechnung rechnung) {
        this.fahrzeug = fahrzeug;
        this.teilnehmer = teilnehmer;
        this.startdatum = startdatum;
        this.enddatum = enddatum;
        this.abgeschlossen = false;
        this.storniert = false;
        this.gefahreneKilometer = gefahreneKilometer;
        this.rechnung = rechnung;
    }

    public Ausleihvorgang() {
    }

    public Ausleihvorgang(Fahrzeug fahrzeugNew, Teilnehmer teilnehmerNew, String startdatum, String enddatum) {
        this.fahrzeug = fahrzeugNew;
        this.teilnehmer = teilnehmerNew;
        this.startdatum = LocalDateTime.parse(startdatum);
        this.enddatum = LocalDateTime.parse(enddatum);
    }

    public Long getAusleihvorgangId() {
        return ausleihvorgangId;
    }

    public void setAusleihvorgangId(Long ausleihvorgangId) {
        this.ausleihvorgangId = ausleihvorgangId;
    }

    public Fahrzeug getFahrzeug() {
        return fahrzeug;
    }

    public void setFahrzeug(Fahrzeug fahrzeug) {
        this.fahrzeug = fahrzeug;
    }

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public LocalDateTime getStartdatum() {
        return startdatum;
    }

    public void setStartdatum(LocalDateTime startzeitpunkt) {
        this.startdatum = startzeitpunkt;
    }

    public LocalDateTime getEnddatum() {
        return enddatum;
    }

    public void setEnddatum(LocalDateTime endzeitpunkt) {
        this.enddatum = endzeitpunkt;
    }

    public boolean getAbgeschlossen() {
        return abgeschlossen;
    }

    public void setAbgeschlossen(boolean abgeschlossen) {
        this.abgeschlossen = abgeschlossen;
    }

    public boolean getStorniert() {
        return storniert;
    }

    public void setStorniert(boolean storniert) {
        this.storniert = storniert;
    }

    public int getGefahreneKilometer() {
        return gefahreneKilometer;
    }

    public void setGefahreneKilometer(int gefahreneKilometer) {
        this.gefahreneKilometer = gefahreneKilometer;
    }

    public Rechnung getRechnung() {
        return rechnung;
    }

    public void setRechnung(Rechnung rechnung) {
        this.rechnung = rechnung;
    }
}
