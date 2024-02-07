package P02_Relations.entities;

import P01_VehicleHierarchy.entities.Car;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "plate_numbers")
public class PlateNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String number;

    @OneToOne(mappedBy = "plateNumber", targetEntity = Car.class)
    private Car car;

    public PlateNumber() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
