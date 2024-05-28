package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.VolcanologistsDto.VolcanologistImportDto;
import softuni.exam.models.dto.VolcanologistsDto.VolcanologistsRootDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private final VolcanologistRepository volcanologistRepository;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final VolcanoRepository volcanoRepository;

    @Autowired
    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser, VolcanoRepository volcanoRepository) {
        this.volcanologistRepository = volcanologistRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.volcanoRepository = volcanoRepository;
    }

    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return Files.readString(Paths.VOLCANOLOGISTS_XML_PATH);
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        final String xml = Paths.VOLCANOLOGISTS_XML_PATH.toAbsolutePath().toString();
        final VolcanologistsRootDto volcanologistsRootDto =
                this.xmlParser.fromFile(xml, VolcanologistsRootDto.class);

        return volcanologistsRootDto.getVolcanologists()
                .stream()
                .map(this::importVolcanologist)
                .collect(Collectors.joining("\n"));
    }

    private String importVolcanologist(VolcanologistImportDto dto) {
        final boolean isValid = this.validationUtil.isValid(dto);

        final Optional<Volcanologist> optionalVolcanologist =
                this.volcanologistRepository
                        .findByFirstNameAndLastName(dto.getFirstName(), dto.getLastName());

        final Optional<Volcano> optionalVolcano = this.volcanoRepository.findById(dto.getVolcano());


        if (!isValid || optionalVolcanologist.isPresent() || optionalVolcano.isEmpty()) {
            return Messages.INVALID_VOLCANOLOGIST;
        }

        final Volcanologist volcanologist = this.modelMapper.map(dto, Volcanologist.class);
        volcanologist.setVolcano(optionalVolcano.get());

        this.volcanologistRepository.saveAndFlush(volcanologist);

        return dto.toString();
    }
}