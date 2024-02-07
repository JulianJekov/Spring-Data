package P01_VehicleHierarchy.entities;

import P02_Relations.entities.Driver;
import P02_Relations.entities.PlateNumber;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "cars")
public class Car extends Vehicle {
    private final static String CAR_TYPE = "CAR";

    private int seats;

    @OneToOne()
    private PlateNumber plateNumber;

    @ManyToMany(mappedBy = "cars", targetEntity = Driver.class)
    private Set<Driver> drivers;

    public Car() {
        super();
    }

    public Car(int seats) {
        super(CAR_TYPE);
        this.seats = seats;
    }


    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}
