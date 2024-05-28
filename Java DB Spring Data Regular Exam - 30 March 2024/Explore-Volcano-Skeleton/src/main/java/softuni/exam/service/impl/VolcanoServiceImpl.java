package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.VolcanoesDto.VolcanoesImportDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanoServiceImpl implements VolcanoService {
    private final static Integer ELEVATION_MORE_THAN = 3000;
    private final VolcanoRepository volcanoRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final CountryRepository countryRepository;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson, CountryRepository countryRepository) {
        this.volcanoRepository = volcanoRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Paths.VOLCANOES_JSON_PATH);
    }

    @Override
    public String importVolcanoes() throws IOException {
        final VolcanoesImportDto[] volcanoesImportDtos =
                this.gson.fromJson(this.readVolcanoesFileContent(), VolcanoesImportDto[].class);

        return Arrays.stream(volcanoesImportDtos)
                .map(this::importVolcanoe)
                .collect(Collectors.joining("\n"));
    }

    private String importVolcanoe(VolcanoesImportDto dto) {
        final boolean isValid = this.validationUtil.isValid(dto);
        final Optional<Volcano> optionalVolcano = this.volcanoRepository.findByName(dto.getName());

        if (!isValid || optionalVolcano.isPresent()) {
            return Messages.INVALID_VOLCANO;
        }
        final Volcano volcano = this.modelMapper.map(dto, Volcano.class);
        final Optional<Country> optionalCountry = this.countryRepository.findById(dto.getCountry());

        volcano.setCountry(optionalCountry.get());

        this.volcanoRepository.saveAndFlush(volcano);

        return dto.toString();
    }

    @Override
    public String exportVolcanoes() {

        final List<Volcano> volcanoes = this.volcanoRepository.
                findByIsActiveTrueAndElevationGreaterThanAndLastEruptionNotNullOrderByElevationDesc
                        (ELEVATION_MORE_THAN);
        return volcanoes.stream().map(Volcano::toString).collect(Collectors.joining("\n"));


    }
}