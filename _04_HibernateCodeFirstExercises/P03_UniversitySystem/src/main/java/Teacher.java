import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teachers")
public class Teacher extends BasePerson{

    @Column(name = "email")
    private String email;

    @Column(name = "salary_per_hour", nullable = false)
    private double salaryPerHour;

    @OneToMany(mappedBy = "teacher", targetEntity = Course.class)
    private Set<Course> courses;

    public Teacher() {
        super();
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(double salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }
}
