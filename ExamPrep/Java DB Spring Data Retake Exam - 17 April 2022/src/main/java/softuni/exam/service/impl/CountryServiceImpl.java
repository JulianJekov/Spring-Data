package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountriesDto.CountriesImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_COUNTRY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_COUNTRY;
import static softuni.exam.constants.Paths.JSON_CONTRIES_PATH;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;


    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(JSON_CONTRIES_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        final String json = this.readCountriesFromFile();
        final CountriesImportDto[] countriesImportDto = gson.fromJson(json, CountriesImportDto[].class);
        return Arrays.stream(countriesImportDto)
                .map(this::importCountry)
                .collect(Collectors.joining("\n"));
    }

    private String importCountry(CountriesImportDto dto) {
        final boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_COUNTRY;
        }

        final Optional<Country> optionalCountry = this.countryRepository.findByCountryName(dto.getCountryName());

        if(optionalCountry.isPresent()) {
            return INVALID_COUNTRY;
        }

        final Country country = this.modelMapper.map(dto, Country.class);

        this.countryRepository.saveAndFlush(country);


        return SUCCESSFULLY_IMPORTED_COUNTRY + country;
    }
}
