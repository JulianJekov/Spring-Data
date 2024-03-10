package com.example.football.service.impl;

import com.example.football.models.dto.TownDtos.TownsImportDto;
import com.example.football.models.entity.Town;
import com.example.football.repository.TownRepository;
import com.example.football.service.TownService;
import com.example.football.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.INVALID_TOWN_MESSAGE;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_TOWN;
import static com.example.football.constants.Paths.JSON_TOWNS_PATH;


@Service
public class TownServiceImpl implements TownService {

    private final TownRepository townRepository;

    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ValidatorUtil validatorUtil, Gson gson, ModelMapper modelMapper) {
        this.townRepository = townRepository;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(JSON_TOWNS_PATH);
    }

    @Override
    public String importTowns() throws IOException {
       final String json = this.readTownsFileContent();

       final TownsImportDto[] townImportDto = this.gson.fromJson(json, TownsImportDto[].class);

        return Arrays.stream(townImportDto)
                .map(this::importTown)
                .collect(Collectors.joining("\n"));
    }

    private String importTown(TownsImportDto dto) {
        final boolean isValid = this.validatorUtil.isValid(dto);

        if(!isValid) {
            return INVALID_TOWN_MESSAGE;
        }

        final Optional<Town> optionalTown = this.townRepository.findByName(dto.getName());

        if(optionalTown.isPresent()) {
            return INVALID_TOWN_MESSAGE;
        }

        final Town town = this.modelMapper.map(dto, Town.class);

        this.townRepository.saveAndFlush(town);

        return SUCCESSFULLY_IMPORTED_TOWN + town;
    }
}
