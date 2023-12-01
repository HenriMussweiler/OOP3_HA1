package core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teilnehmer")
public class Teilnehmer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teilnehmer_generator")
    @Column(name = "teilnehmer_id")
    private Long teilnehmerId;

    @Column(name = "name")
    private String name;

    @Column(name = "vorname")
    private String vorname;

    @Column(name = "strasse")
    private String strasse;

    @Column(name = "hausnummer")
    private String hausnummer;

    @Column(name = "postleitzahl")
    private String postleitzahl;

    @Column(name = "ort")
    private String ort;

    @Column(name = "iban")
    private String iban;

    @Column(name = "mail")
    private String mail;

    @Column(name = "telefon")
    private String telefon;

    public Teilnehmer() {
    }

    public Teilnehmer(String name, String vorname, String strasse, String hausnummer, String postleitzahl, String ort, String iban, String mail, String telefon) {
        this.name = name;
        this.vorname = vorname;
        this.strasse = strasse;
        this.hausnummer = hausnummer;
        this.postleitzahl = postleitzahl;
        this.ort = ort;
        this.iban = iban;
        this.mail = mail;
        this.telefon = telefon;
    }

    public Long getTeilnehmerId() {
        return teilnehmerId;
    }

    public void setTeilnehmerId(Long teilnehmerId) {
        this.teilnehmerId = teilnehmerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnummer() {
        return hausnummer;
    }

    public void setHausnummer(String hausnummer) {
        this.hausnummer = hausnummer;
    }

    public String getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(String postleitzahl) {
        this.postleitzahl = postleitzahl;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
