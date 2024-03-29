package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.constants.Messages;
import softuni.exam.constants.Paths;
import softuni.exam.models.dto.PartsDtos.PartsImportDto;
import softuni.exam.models.entity.Part;
import softuni.exam.repository.PartsRepository;
import softuni.exam.service.PartsService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartsServiceImpl implements PartsService {

    private final PartsRepository partsRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PartsServiceImpl(PartsRepository partsRepository, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.partsRepository = partsRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.partsRepository.count() > 0;
    }

    @Override
    public String readPartsFileContent() throws IOException {
        return Files.readString(Paths.PARTS_JSON_PATH);
    }

    @Override

    public String importParts() throws IOException {
        final PartsImportDto[] partsImportDtos =
                this.gson.fromJson(this.readPartsFileContent(), PartsImportDto[].class);

        return Arrays.stream(partsImportDtos)
                .map(this::importPart)
                .collect(Collectors.joining("\n"));
    }

    private String importPart(PartsImportDto partsImportDto) {
        final boolean isValid = this.validatorUtil.isValid(partsImportDto);

        final Optional<Part> optionalPart = this.partsRepository.findByPartName(partsImportDto.getPartName());

        if (!isValid || optionalPart.isPresent()) {
            return Messages.INVALID_PART;
        }

        final Part part = modelMapper.map(partsImportDto, Part.class);

        this.partsRepository.saveAndFlush(part);

        return partsImportDto.toString();
    }

}
