package core.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "sharing_standort")
public class SharingStandort {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sharing_standort_generator")
    @Column(name = "sharing_standort_id")
    private Long sharingStandortId;

    @Column(name = "standort_name")
    private String standortName;

    @OneToMany(mappedBy = "sharingStandort")
    private List<Fahrzeug> fahrzeuge;

    // Konstruktor, Getter und Setter hier...

    public SharingStandort() {
    }

    public SharingStandort(Long sharingStandortId, String standortName) {
        this.sharingStandortId = sharingStandortId;
        this.standortName = standortName;
    }

    public SharingStandort(String sharingStandort) {
        this.standortName = sharingStandort;
    }

    public Long getSharingStandortId() {
        return sharingStandortId;
    }

    public void setSharingStandortId(Long sharingStandortId) {
        this.sharingStandortId = sharingStandortId;
    }

    public String getStandortName() {
        return standortName;
    }

    public void setStandortName(String standortName) {
        this.standortName = standortName;
    }

    // Weitere Methoden und Annotationen können je nach Bedarf hinzugefügt werden.
}
