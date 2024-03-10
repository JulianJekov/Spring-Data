package softuni.exam.models.dto.CountriesDto;

import javax.validation.constraints.Size;

public class CountriesImportDto {

    @Size(min = 2, max = 60)
    private String countryName;

    @Size(min = 2, max = 20)
    private String currency;

    public CountriesImportDto() {
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCurrency() {
        return currency;
    }
}
