package P01_VehicleHierarchy.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "trucks")
public class Truck extends Vehicle {
    private static final String TRUCK_TYPE = "TRUCK";

    @Column(name = "load_capacity")
    private double loadCapacity;

    public Truck() {
        super();
    }

    public Truck(double loadCapacity) {
        super(TRUCK_TYPE);
        this.loadCapacity = loadCapacity;
    }

    public double getLoadCapacity() {
        return loadCapacity;
    }

    public void setLoadCapacity(double loadCapacity) {
        this.loadCapacity = loadCapacity;
    }
}

