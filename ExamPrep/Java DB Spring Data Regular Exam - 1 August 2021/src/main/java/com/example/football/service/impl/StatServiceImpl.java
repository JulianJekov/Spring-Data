package com.example.football.service.impl;

import com.example.football.models.dto.StatDto.StatsImportDto;
import com.example.football.models.dto.StatDto.StatsImportRootDto;
import com.example.football.models.entity.Stat;
import com.example.football.repository.StatRepository;
import com.example.football.service.StatService;
import com.example.football.util.ValidatorUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.*;
import static com.example.football.constants.Paths.XML_STATS_PATH;

@Service
public class StatServiceImpl implements StatService {

    private final StatRepository statRepository;

    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public StatServiceImpl(StatRepository statRepository, ValidatorUtil validatorUtil, XmlParser xmlParser, ModelMapper modelMapper) {
        this.statRepository = statRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.statRepository.count() > 0;
    }

    @Override
    public String readStatsFileContent() throws IOException {
        return Files.readString(XML_STATS_PATH);
    }

    @Override
    public String importStats() throws JAXBException, IOException {
        String xml = XML_STATS_PATH.toAbsolutePath().toString();

        StatsImportRootDto statsImportDto =
                xmlParser.convertFromFile(xml, StatsImportRootDto.class);

        return statsImportDto.getStats().stream()
                .map(this::importStat)
                .collect(Collectors.joining("\n"));
    }

    private String importStat(StatsImportDto dto) {
        boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_STAT_MESSAGE;
        }

        Optional<Stat> optionalStat =
                this.statRepository.findByPassingAndShootingAndEndurance
                        (dto.getPassing(), dto.getShooting(), dto.getEndurance());

        if(optionalStat.isPresent()) {
            return INVALID_STAT_MESSAGE;
        }

        Stat stat = this.modelMapper.map(dto, Stat.class);

        this.statRepository.saveAndFlush(stat);
        return SUCCESSFULLY_IMPORTED_STAT + stat;
    }
}
