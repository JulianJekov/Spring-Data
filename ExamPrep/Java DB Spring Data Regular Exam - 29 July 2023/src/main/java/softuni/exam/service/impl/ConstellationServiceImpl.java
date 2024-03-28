package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.ConstellationDto.ConstellationImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private final ConstellationRepository ConstellationRepository;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(softuni.exam.repository.ConstellationRepository constellationRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.ConstellationRepository = constellationRepository;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.ConstellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Paths.CONSTELLATIONS_JSON_IMPORT_PATH);
    }

    @Override
    public String importConstellations() throws IOException {
        final ConstellationImportDto[] constellationImportDtos =
                gson.fromJson(this.readConstellationsFromFile(), ConstellationImportDto[].class);

        return Arrays.stream(constellationImportDtos)
                .map(this::importConstellation)
                .collect(Collectors.joining("\n"));

    }

    private String importConstellation(ConstellationImportDto dto) {
        final boolean isValid = validatorUtil.isValid(dto);

        final Optional<Constellation> optionalConstellation = this.ConstellationRepository.findByName(dto.getName());

        if (!isValid || optionalConstellation.isPresent()) {
            return Messages.INVALID_CONSTELLATION;
        }

        final Constellation constellation = modelMapper.map(dto, Constellation.class);

        this.ConstellationRepository.saveAndFlush(constellation);

        return dto.toString();
    }
}
