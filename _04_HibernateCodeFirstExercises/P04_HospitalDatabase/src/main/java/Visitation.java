import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "visitations")
public class Visitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "visitation_date", nullable = false)
    private LocalDate visitationDate;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne(optional = false)
    @JoinColumn(name = "diagnose_id", referencedColumnName = "id")
    private Diagnose diagnose;

    @ManyToOne(optional = false)
    @JoinColumn(name = "medicament_id", referencedColumnName = "id")
    private Medicament medicament;

    public Visitation() {
    }



    public Medicament getMedicament() {
        return medicament;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Diagnose getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(Diagnose diagnose) {
        this.diagnose = diagnose;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getVisitationDate() {
        return visitationDate;
    }

    public void setVisitationDate(LocalDate visitationDate) {
        this.visitationDate = visitationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
