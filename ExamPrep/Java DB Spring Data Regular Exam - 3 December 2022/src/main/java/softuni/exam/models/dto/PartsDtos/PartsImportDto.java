package softuni.exam.models.dto.PartsDtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class PartsImportDto {

    @Size(min = 2, max = 19)
    private String partName;

    @Min(10)
    @Max(2000)
    private Double price;

    @Positive
    private Integer quantity;

    public PartsImportDto() {
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

    @Override
    public String toString() {
        return String.format("Successfully imported part %s - %s", this.partName, this.price);
    }
}
