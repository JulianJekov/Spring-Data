package com.example.football.service.impl;

import com.example.football.models.dto.TeamDtos.TeamsImportDto;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.TeamService;
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

import static com.example.football.constants.Messages.*;
import static com.example.football.constants.Paths.JSON_TEAMS_PATH;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    private final ValidatorUtil validatorUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final TownRepository townRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, ValidatorUtil validatorUtil, Gson gson, ModelMapper modelMapper, TownRepository townRepository) {
        this.teamRepository = teamRepository;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() > 0;
    }

    @Override
    public String readTeamsFileContent() throws IOException {
        return Files.readString(JSON_TEAMS_PATH);
    }

    @Override
    public String importTeams() throws IOException {
        final String json = this.readTeamsFileContent();
        final TeamsImportDto[] teamsImportDto = this.gson.fromJson(json, TeamsImportDto[].class);
        return Arrays.stream(teamsImportDto)
                .map(this::importTeam)
                .collect(Collectors.joining("\n"));
    }

    private String importTeam(TeamsImportDto dto) {

        final boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_TEAM_MESSAGE;
        }

        final Optional<Team> optionalTeam = this.teamRepository.findByName(dto.getName());

        if (optionalTeam.isPresent()) {
            return INVALID_TEAM_MESSAGE;
        }

        final Optional<Town> town = this.townRepository.findByName(dto.getTownName());

        final Team team = this.modelMapper.map(dto, Team.class);

        team.setTown(town.get());

        this.teamRepository.saveAndFlush(team);

        return SUCCESSFULLY_IMPORTED_TEAM + team;
    }
}
