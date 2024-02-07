package P01_VehicleHierarchy.entities;

import P02_Relations.entities.Company;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = "planes")
public class Plane extends Vehicle {
    private final static String PLANE_TYPE = "PLANE";

    @Column(name = "passenger_capacity")
    private int passengerCapacity;

    private String airline;

    @ManyToOne
    private Company company;

    public Plane() {
        super();
    }

    public Plane(int passengerCapacity, String airline) {
        super(PLANE_TYPE);
        this.passengerCapacity = passengerCapacity;
        this.airline = airline;
    }

    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    public void setPassengerCapacity(int passengerCapacity) {
        this.passengerCapacity = passengerCapacity;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }
}
