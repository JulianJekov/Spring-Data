package P01_VehicleHierarchy.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue(value = "bikes")
public class Bike extends Vehicle {
    private final static String BIKE_TYPE = "BIKE";

    public Bike() {
        super();
    }

    public Bike(String type) {
        super(BIKE_TYPE);
    }
}
