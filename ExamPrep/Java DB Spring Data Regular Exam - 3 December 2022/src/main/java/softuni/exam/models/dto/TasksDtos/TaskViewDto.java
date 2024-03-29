package softuni.exam.models.dto.TasksDtos;

import java.math.BigDecimal;

public class TaskViewDto {

    private String carMake;

    private String carModel;

    private Integer carKilometers;

    private String mechanicFirstName;

    private String mechanicLastName;

    private Long id;

    private Double carEngine;

    private BigDecimal price;

    public TaskViewDto() {
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Integer getCarKilometers() {
        return carKilometers;
    }

    public void setCarKilometers(Integer carKilometers) {
        this.carKilometers = carKilometers;
    }

    public String getMechanicFirstName() {
        return mechanicFirstName;
    }

    public void setMechanicFirstName(String mechanicFirstName) {
        this.mechanicFirstName = mechanicFirstName;
    }

    public String getMechanicLastName() {
        return mechanicLastName;
    }

    public void setMechanicLastName(String mechanicLastName) {
        this.mechanicLastName = mechanicLastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(Double carEngine) {
        this.carEngine = carEngine;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Car %s %s with %dkm%n" +
                "-Mechanic: %s %s - task №%d:%n" +
                " --Engine: %s%n" +
                "---Price: %.2f$",
                this.carMake, this.carModel, this.carKilometers,
                this.mechanicFirstName, this.mechanicLastName,
                this.id, this.carEngine, this.price);
    }
}
