package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.MechanicsDtos.MechanicImportDto;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MechanicsServiceImpl implements MechanicsService {

    private final MechanicsRepository mechanicsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public MechanicsServiceImpl(MechanicsRepository mechanicsRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.mechanicsRepository = mechanicsRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.mechanicsRepository.count() > 0;
    }

    @Override
    public String readMechanicsFromFile() throws IOException {
        return Files.readString(Paths.MECHANICS_JSON_PATH);
    }

    @Override
    public String importMechanics() throws IOException {
       final MechanicImportDto[] mechanicImportDtos =
               this.gson.fromJson(this.readMechanicsFromFile(), MechanicImportDto[].class);

        return Arrays.stream(mechanicImportDtos)
                .map(this::importMechanic)
                .collect(Collectors.joining("\n"));
    }

    private String importMechanic(MechanicImportDto mechanicImportDto) {
        final boolean isValid = this.validatorUtil.isValid(mechanicImportDto);
        final Optional<Mechanic> optionalMechanic = this.mechanicsRepository.findByEmail(mechanicImportDto.getEmail());

        if(!isValid || optionalMechanic.isPresent()) {
            return Messages.INVALID_MECHANIC;
        }

        final Mechanic mechanic = this.modelMapper.map(mechanicImportDto, Mechanic.class);

        this.mechanicsRepository.saveAndFlush(mechanic);
        return mechanicImportDto.toString();
    }
}
