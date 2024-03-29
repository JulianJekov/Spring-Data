package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.constants.Paths;
import softuni.exam.repository.MechanicsRepository;
import softuni.exam.service.MechanicsService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;

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
        return null;
    }
}
