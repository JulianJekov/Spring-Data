package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CitiesDto.CitiesImportDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CityService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_CITY;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_CITY;
import static softuni.exam.constants.Paths.JSON_CITIES_PATH;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final CountryRepository countryRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, CountryRepository countryRepository) {
        this.cityRepository = cityRepository;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files.readString(JSON_CITIES_PATH);
    }

    @Override
    public String importCities() throws IOException {

        final String json = this.readCitiesFileContent();
        final CitiesImportDto[] citiesImportDto = this.gson.fromJson(json, CitiesImportDto[].class);

        return Arrays.stream(citiesImportDto)
                .map(this::importCity)
                .collect(Collectors.joining("\n"));

    }

    private String importCity(CitiesImportDto dto) {

        final boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_CITY;
        }

        final Optional<City> optionalCity = this.cityRepository.findByCityName(dto.getCityName());

        if (optionalCity.isPresent()) {
            return INVALID_CITY;
        }

        final City city = modelMapper.map(dto, City.class);

        final Optional<Country> optionalCountry = this.countryRepository.findById(dto.getCountry());
        final Country country = optionalCountry.get();
        city.setCountry(country);

        this.cityRepository.saveAndFlush(city);
        return SUCCESSFULLY_IMPORTED_CITY + city;
    }
}
