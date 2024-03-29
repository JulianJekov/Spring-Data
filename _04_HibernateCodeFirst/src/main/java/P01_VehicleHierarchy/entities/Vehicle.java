package P01_VehicleHierarchy.entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name = "type")

public abstract class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected long id;

    @Column(insertable = false, updatable = false)
    protected String type;

    protected String model;

    protected BigDecimal price;

    @Column(name = "fuel_type")
    protected String fuelType;

    public Vehicle() {
    }

    protected Vehicle(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }
}
