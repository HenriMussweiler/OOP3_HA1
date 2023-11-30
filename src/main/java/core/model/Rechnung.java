package core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rechnung")
public class Rechnung {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rechnung_generator")
    @Column(name = "rechnung_id")
    private Long rechnungId;

    @ManyToOne
    @JoinColumn(name = "teilnehmer_id")
    private Teilnehmer teilnehmer;

    @Column(name = "rechnungsdatum")
    private LocalDateTime rechnungsdatum;

    @OneToMany(mappedBy = "rechnung", cascade = CascadeType.MERGE)
    private List<Ausleihvorgang> ausleihvorgange = new ArrayList<>();

    public void setAusleihvorgaenge(List<Ausleihvorgang> ausleihvorgange) {
        this.ausleihvorgange = ausleihvorgange;
    }

    public void addAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        ausleihvorgang.setRechnung(this);
        ausleihvorgange.add(ausleihvorgang);
    }

    public void removeAusleihvorgang(Ausleihvorgang ausleihvorgang) {
        ausleihvorgange.remove(ausleihvorgang);
        ausleihvorgang.setRechnung(null);
    }

    @Column(name = "gesamtsumme")
    private double gesamtsumme;

    // Konstruktor, Getter und Setter hier...

    public Rechnung(Teilnehmer teilnehmer, LocalDateTime rechnungsdatum, List<Ausleihvorgang> ausleihvorgange, double gesamtsumme) {
        this.teilnehmer = teilnehmer;
        this.rechnungsdatum = rechnungsdatum;
        this.ausleihvorgange = ausleihvorgange;
        this.gesamtsumme = gesamtsumme;
    }

    public Rechnung (Teilnehmer teilnehmer, LocalDateTime rechnungsdatum, double gesamtsumme) {
        this.teilnehmer = teilnehmer;
        this.rechnungsdatum = rechnungsdatum;
        this.gesamtsumme = gesamtsumme;
    }

    public Rechnung() {
    }

    public Long getRechnungId() {
        return rechnungId;
    }

    public void setRechnungId(Long rechnungId) {
        this.rechnungId = rechnungId;
    }

    public Teilnehmer getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(Teilnehmer teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public LocalDateTime getRechnungsdatum() {
        return rechnungsdatum;
    }

    public void setRechnungsdatum(LocalDateTime rechnungsdatum) {
        this.rechnungsdatum = rechnungsdatum;
    }

    public List<Ausleihvorgang> getAusleihvorgange() {
        return ausleihvorgange;
    }

    public void setAusleihvorgange(List<Ausleihvorgang> ausleihvorgange) {
        this.ausleihvorgange = ausleihvorgange;
    }

    public double getGesamtsumme() {
        return gesamtsumme;
    }

    public void setGesamtsumme(double gesamtsumme) {
        this.gesamtsumme = gesamtsumme;
    }

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
