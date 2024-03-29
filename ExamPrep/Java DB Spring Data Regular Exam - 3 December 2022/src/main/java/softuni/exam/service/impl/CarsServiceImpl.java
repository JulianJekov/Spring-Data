package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.CarsDtos.CarImportDto;
import softuni.exam.models.dto.CarsDtos.CarsImportRootDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarsRepository;
import softuni.exam.service.CarsService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsServiceImpl implements CarsService {

    private static String CARS_FILE_PATH = "src/main/resources/files/xml/cars.xml";

    private final CarsRepository carsRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validatorUtil;


    @Autowired
    public CarsServiceImpl(CarsRepository carsRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validatorUtil) {
        this.carsRepository = carsRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.carsRepository.count() > 0;
    }

    @Override
    public String readCarsFromFile() throws IOException {
        return Files.readString(Paths.CARS_XML_PATH);
    }

    @Override
    public String importCars() throws IOException, JAXBException {

       final CarsImportRootDto carsImportRootDto = xmlParser.convertFromFile(CARS_FILE_PATH, CarsImportRootDto.class);
        return carsImportRootDto.getCars()
                .stream()
                .map(this::importCar)
                .collect(Collectors.joining("\n"));
    }

    private String importCar(CarImportDto carImportDto) {
        final boolean isValid = validatorUtil.isValid(carImportDto);
        final Optional<Car> optionalCar = this.carsRepository.findByPlateNumber(carImportDto.getPlateNumber());

        if(!isValid || optionalCar.isPresent()) {
            return Messages.INVALID_CAR;
        }

        final Car car = this.modelMapper.map(carImportDto, Car.class);

        this.carsRepository.saveAndFlush(car);

        return carImportDto.toString();
    }
}
