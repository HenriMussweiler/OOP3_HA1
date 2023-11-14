package core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rechnung")
public class Rechnung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rechnung_id")
    private Long rechnungId;

    @ManyToOne
    @JoinColumn(name = "teilnehmer_id")
    private Teilnehmer teilnehmer;

    @Column(name = "rechnungsdatum")
    private LocalDateTime rechnungsdatum;

    @OneToMany(mappedBy = "rechnung")
    private List<Ausleihvorgang> ausleihvorgange;

    @Column(name = "gesamtsumme")
    private double gesamtsumme;

    // Konstruktor, Getter und Setter hier...

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
