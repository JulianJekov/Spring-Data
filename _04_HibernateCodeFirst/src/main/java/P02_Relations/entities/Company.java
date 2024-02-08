package P02_Relations.entities;

import P01_VehicleHierarchy.entities.Plane;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToMany
    private Set<Plane> planes;

    public Company() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Plane> getPlanes() {
        return planes;
    }

    public void setPlanes(Set<Plane> planes) {
        this.planes = planes;
    }
}
