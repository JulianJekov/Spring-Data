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
}
