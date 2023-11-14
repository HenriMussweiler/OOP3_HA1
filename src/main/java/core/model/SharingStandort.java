package core.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sharing_standort")
public class SharingStandort {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_standort_id")
    private Long sharingStandortId;

    @Column(name = "standort_name")
    private String standortName;

    @OneToMany(mappedBy = "sharingStandort")
    private List<Fahrzeug> fahrzeuge;

    // Konstruktor, Getter und Setter hier...

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
