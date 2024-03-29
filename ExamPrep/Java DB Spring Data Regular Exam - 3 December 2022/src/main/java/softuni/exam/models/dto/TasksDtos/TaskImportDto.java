package softuni.exam.models.dto.TasksDtos;

import softuni.exam.models.dto.CarsDtos.CarIdDto;
import softuni.exam.models.dto.MechanicsDtos.MechanicNameDto;
import softuni.exam.models.dto.PartsDtos.PartIdDto;
import softuni.exam.util.LocalDateTimeAdapter;

import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class TaskImportDto {

    @XmlElement
    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime date;
    @XmlElement
    @Positive
    private BigDecimal price;
    @XmlElement
    private CarIdDto car;
    @XmlElement
    private MechanicNameDto mechanic;
    @XmlElement
    private PartIdDto part;

    public TaskImportDto() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarIdDto getCar() {
        return car;
    }

    public void setCar(CarIdDto car) {
        this.car = car;
    }

    public MechanicNameDto getMechanic() {
        return mechanic;
    }

    public void setMechanic(MechanicNameDto mechanic) {
        this.mechanic = mechanic;
    }

    public PartIdDto getPart() {
        return part;
    }

    public void setPart(PartIdDto part) {
        this.part = part;
    }

    @Override
    public String toString() {
        return String.format("Successfully imported task %.2f", this.price);
    }
}
