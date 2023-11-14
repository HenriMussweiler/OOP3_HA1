package core.model;

import jakarta.persistence.*;

@Entity
@Table(name = "teilnehmer")
public class Teilnehmer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // Konstruktor, Getter und Setter hier...

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
