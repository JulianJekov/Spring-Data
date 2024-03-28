package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.StarDto.StarViewDto;
import softuni.exam.models.dto.StarDto.StarsImportDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;
    private final ValidatorUtil validatorUtil;
    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public StarServiceImpl(StarRepository starRepository, ValidatorUtil validatorUtil, ConstellationRepository constellationRepository, Gson gson, ModelMapper modelMapper) {
        this.starRepository = starRepository;
        this.validatorUtil = validatorUtil;
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        return Files.readString(Paths.STARS_JSON_IMPORT_PATH);
    }

    @Override
    public String importStars() throws IOException {

        final StarsImportDto[] starsImportDtos = this.gson.fromJson(this.readStarsFileContent(), StarsImportDto[].class);

        return Arrays.stream(starsImportDtos)
                .map(this::importStar)
                .collect(Collectors.joining("\n"));
    }

    private String importStar(StarsImportDto dto) {

        final boolean isValid = this.validatorUtil.isValid(dto);

        final Optional<Star> optionalStar = this.starRepository.findByName(dto.getName());

        if (!isValid || optionalStar.isPresent()) {
            return Messages.INVALID_STAR;
        }

        final Optional<Constellation> optionalConstellation = this.constellationRepository.findById(dto.getConstellation());

        final Star star = this.modelMapper.map(dto, Star.class);

        star.setConstellation(optionalConstellation.get());

        this.starRepository.saveAndFlush(star);

        return dto.toString();
    }

    @Override
    public String exportStars() {
        final StarType starType = StarType.RED_GIANT;
        final Set<Star> starTypeAndObserversEmpty =
                this.starRepository.findByStarTypeAndObserversEmptyOrderByLightYearsAsc(starType);

        return starTypeAndObserversEmpty.stream()
                .map(s -> this.modelMapper.map(s, StarViewDto.class))
                .map(StarViewDto::toString)
                .collect(Collectors.joining("\n"));
    }
}
