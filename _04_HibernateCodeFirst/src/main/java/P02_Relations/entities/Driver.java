package P02_Relations.entities;

import P01_VehicleHierarchy.entities.Car;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany
    @JoinTable(
            name = "drivers_cars",
            joinColumns = @JoinColumn(name = "driver_id" ,referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id")
    )
    private Set<Car> cars;

    public Driver() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Set<Car> getCars() {
        return cars;
    }

    public void setCars(Set<Car> cars) {
        this.cars = cars;
    }
}
