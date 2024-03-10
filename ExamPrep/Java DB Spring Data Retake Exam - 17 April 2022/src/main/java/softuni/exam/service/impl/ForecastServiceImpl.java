package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ForecastDto.ForecastImportDto;
import softuni.exam.models.dto.ForecastDto.ForecastsImportRootDto;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.DayOfWeek;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.CityRepository;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_FORECAST;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED_FORECAST;
import static softuni.exam.constants.Paths.XML_FORECASTS_PATH;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final ForecastRepository forecastRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final CityRepository cityRepository;

    @Autowired
    public ForecastServiceImpl(ForecastRepository forecastRepository, ModelMapper modelMapper, ValidatorUtil validatorUtil, XmlParser xmlParser, CityRepository cityRepository) {
        this.forecastRepository = forecastRepository;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.cityRepository = cityRepository;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(XML_FORECASTS_PATH);
    }

    @Override

    public String importForecasts() throws IOException, JAXBException {
        String xml = XML_FORECASTS_PATH.toAbsolutePath().toString();
        ForecastsImportRootDto forecastsImportRootDto = xmlParser.convertFromFile(xml, ForecastsImportRootDto.class);
        return forecastsImportRootDto.getForecasts()
                .stream()
                .map(this::importForecast)
                .collect(Collectors.joining("\n"));
    }

    private String importForecast(ForecastImportDto dto) {
        boolean isValid = this.validatorUtil.isValid(dto);
        if (!isValid) {
            return INVALID_FORECAST;
        }

        Optional<Forecast> optionalForecast = this.forecastRepository.findByDayOfWeekAndCityId(dto.getDayOfWeek(), dto.getCity());

        if (optionalForecast.isPresent()) {
            return INVALID_FORECAST;
        }

        Forecast forecast = this.modelMapper.map(dto, Forecast.class);
        City city = this.cityRepository.findById(dto.getCity()).get();
        forecast.setCity(city);

        this.forecastRepository.saveAndFlush(forecast);
        return SUCCESSFULLY_IMPORTED_FORECAST + forecast.getDayOfWeek().toString() + " - " + forecast.getMaxTemperature();
    }

    @Override
    public String exportForecasts() {
        DayOfWeek sunday = DayOfWeek.SUNDAY;
        int lessThanCitizens = 150000;
        List<Forecast> sundayForecastInfo = this.forecastRepository.
                findAllByDayOfWeekIsAndCityPopulationLessThanOrderByMaxTemperatureDescIdAsc(sunday, lessThanCitizens);

        return String.join("\n", sundayForecastInfo.toString());
    }
}
