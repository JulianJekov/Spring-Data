package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "parts")
public class Part extends BaseEntity{

    @Column(unique = true, nullable = false, name = "part_name")
    private String partName;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @OneToMany(mappedBy = "part", targetEntity = Task.class, fetch = FetchType.EAGER)
    private Set<Task> task;

    public Part() {
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }
}
