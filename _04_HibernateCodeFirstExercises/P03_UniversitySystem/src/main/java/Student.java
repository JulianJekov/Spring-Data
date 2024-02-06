import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "students")
public class Student extends BasePerson {

    @Column(name = "average_grade")
    private double averageGrade;

    @Column(name = "attendance", nullable = false)
    private int attendance;

    @ManyToMany
    @JoinTable(
            name = "students_courses",
            joinColumns = @JoinColumn(name = "students_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id", referencedColumnName = "id")
    )
    private Set<Course> courses;

    public Student() {
        super();
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }
}
