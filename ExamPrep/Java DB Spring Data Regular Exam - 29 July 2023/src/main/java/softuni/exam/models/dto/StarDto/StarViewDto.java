package softuni.exam.models.dto.StarDto;

public class StarViewDto {

    private String name;

    private Double lightYears;

    private String description;

    private String constellationName;

    public StarViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLightYears() {
        return lightYears;
    }

    public void setLightYears(Double lightYears) {
        this.lightYears = lightYears;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConstellationName() {
        return constellationName;
    }

    public void setConstellationName(String constellationName) {
        this.constellationName = constellationName;
    }

    @Override
    public String toString() {
        return String.format("Star: %s%n" +
                "   *Distance: %.2f light years%n" +
                "   **Description: %s%n" +
                "   ***Constellation: %s",
                this.name, this.lightYears,
                this.description, this.constellationName);
    }
}
