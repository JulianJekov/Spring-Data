package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CountriesDto.CountriesImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.countryRepository = countryRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Paths.COUNTRIES_JSON_PATH);
    }

    @Override
    public String importCountries() throws IOException {
        final CountriesImportDto[] countriesImportDtos =
                this.gson.fromJson(this.readCountriesFromFile(), CountriesImportDto[].class);

        return Arrays.stream(countriesImportDtos)
                .map(this::importCountrie)
                .collect(Collectors.joining("\n"));
    }

    private String importCountrie(CountriesImportDto dto) {
        final boolean isValid = this.validationUtil.isValid(dto);

        final Optional<Country> optionalCountry = this.countryRepository.findByName(dto.getName());

        if (!isValid || optionalCountry.isPresent()) {
            return Messages.INVALID_COUNTRY;
        }

        final Country country = this.modelMapper.map(dto, Country.class);

        this.countryRepository.saveAndFlush(country);
        return dto.toString();
    }
}
