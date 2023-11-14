package core.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ausleihvorgang")
public class Ausleihvorgang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ausleihvorgang_id")
    private Long ausleihvorgangId;

    @ManyToOne
    @JoinColumn(name = "fahrzeug_id")
    private Fahrzeug fahrzeug;

    @ManyToOne
    @JoinColumn(name = "teilnehmer_id")
    private Teilnehmer teilnehmer;

    @Column(name = "startzeitpunkt")
    private LocalDateTime startzeitpunkt;

    @Column(name = "endzeitpunkt")
    private LocalDateTime endzeitpunkt;

    @Column(name = "gefahrene_kilometer")
    private int gefahreneKilometer;

    @ManyToOne
    @JoinColumn(name = "rechnung_id")
    private Rechnung rechnung;

    // Konstruktor, Getter und Setter hier...

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
