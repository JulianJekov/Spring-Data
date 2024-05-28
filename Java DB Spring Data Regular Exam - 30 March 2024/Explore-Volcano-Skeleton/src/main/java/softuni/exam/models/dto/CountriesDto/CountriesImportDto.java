package softuni.exam.models.dto.CountriesDto;

import javax.validation.constraints.Size;

public class CountriesImportDto {

    @Size(min = 3, max = 30)
    private String name;

    @Size(min = 3, max = 30)
    private String capital;

    public CountriesImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    @Override
    public String toString() {
        return String.format("Successfully imported country %s - %s", this.name, this.capital);
    }
}
