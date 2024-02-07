import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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


    public Visitation() {
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
