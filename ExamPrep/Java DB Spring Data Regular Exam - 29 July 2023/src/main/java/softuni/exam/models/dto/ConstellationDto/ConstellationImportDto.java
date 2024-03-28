package softuni.exam.models.dto.ConstellationDto;

import javax.validation.constraints.Size;

public class ConstellationImportDto {

    @Size(min = 3, max = 20)
    private String name;

    @Size(min = 5)
    private String description;

    public ConstellationImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Successfully imported constellation %s - %s", this.name, this.description);
    }
}

