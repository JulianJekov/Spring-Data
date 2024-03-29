package softuni.exam.models.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "parts_id")
    private Part part;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Mechanic mechanic;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cars_id")
    private Car car;

    public Task() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public Mechanic getMechanic() {
        return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
        this.mechanic = mechanic;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm%n" +
                        "-Mechanic: %s %s - task №%d:%n" +
                        " --Engine: %s%n" +
                        "---Price: %.2f$", getCar().getCarMake(), getCar().getCarModel(), getCar().getKilometers(),
                getMechanic().getFirstName(), getMechanic().getLastName(), getId(), getCar().getEngine(),
                getPrice());
    }
}
