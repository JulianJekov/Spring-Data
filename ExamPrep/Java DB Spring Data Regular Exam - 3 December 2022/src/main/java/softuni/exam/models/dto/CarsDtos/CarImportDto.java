package softuni.exam.models.dto.CarsDtos;

import softuni.exam.models.entity.CarType;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDto {

    @XmlElement(name = "carMake")
    @Size(min = 2, max = 30)
    private String carMake;

    @XmlElement(name = "carModel")
    @Size(min = 2, max = 30)
    private String carModel;

    @XmlElement
    @Positive
    private Integer year;

    @XmlElement(name = "plateNumber")
    @Size(min = 2, max = 30)
    private String plateNumber;

    @XmlElement
    @Positive
    private Integer kilometers;

    @XmlElement
    @Min(1)
    private Double engine;

    @XmlElement(name = "carType")
    private CarType carType;

    public CarImportDto() {
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public Double getEngine() {
        return engine;
    }

    public void setEngine(Double engine) {
        this.engine = engine;
    }

    public CarType getCarType() {
        return carType;
    }

    public void setCarType(CarType carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return String.format("Successfully imported car %s - %s", this.carMake, this.carModel);
    }
}
