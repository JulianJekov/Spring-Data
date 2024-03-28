package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.AstronomerDto.AstronomerImportDto;
import softuni.exam.models.dto.AstronomerDto.AstronomerImportRootDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AstronomerServiceImpl implements AstronomerService {

    private final AstronomerRepository astronomerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final StarRepository starRepository;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, StarRepository starRepostiory, StarRepository starRepository) {
        this.astronomerRepository = astronomerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.starRepository = starRepository;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Paths.ASTRONOMERS_XML_IMPORT_PATH);
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {
        String xml = Paths.ASTRONOMERS_XML_IMPORT_PATH.toAbsolutePath().toString();
        final AstronomerImportRootDto astronomerImportRootDto =
                this.xmlParser.convertFromFile(xml, AstronomerImportRootDto.class);

        return astronomerImportRootDto.getAstronomers().stream()
                .map(this::importAstronomer)
                .collect(Collectors.joining("\n"));

    }

    private String importAstronomer(AstronomerImportDto dto) {
        final boolean isValid = this.validatorUtil.isValid(dto);

        final Optional<Astronomer> optionalAstronomer =
                this.astronomerRepository.findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());

        final Optional<Star> optionalStar = this.starRepository.findById(dto.getStar());

        if (!isValid || optionalAstronomer.isPresent() || optionalStar.isEmpty()) {
            return Messages.INVALID_ASTRONOMER;
        }

        final Astronomer astronomer = this.modelMapper.map(dto, Astronomer.class);
        astronomer.setStar(optionalStar.get());

        this.astronomerRepository.saveAndFlush(astronomer);

        return dto.toString();
    }
}
