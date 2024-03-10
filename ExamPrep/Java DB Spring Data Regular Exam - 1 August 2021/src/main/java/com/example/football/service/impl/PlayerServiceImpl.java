package com.example.football.service.impl;

import com.example.football.models.dto.PlayerDto.PlayersImportDto;
import com.example.football.models.dto.PlayerDto.PlayersImportRootDto;
import com.example.football.models.entity.Player;
import com.example.football.models.entity.Stat;
import com.example.football.models.entity.Team;
import com.example.football.models.entity.Town;
import com.example.football.repository.PlayerRepository;
import com.example.football.repository.StatRepository;
import com.example.football.repository.TeamRepository;
import com.example.football.repository.TownRepository;
import com.example.football.service.PlayerService;
import com.example.football.util.ValidatorUtil;
import com.example.football.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.football.constants.Messages.INVALID_PLAYER_MESSAGE;
import static com.example.football.constants.Messages.SUCCESSFULLY_IMPORTED_PLAYER;
import static com.example.football.constants.Paths.XML_PLAYER_PATH;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final TownRepository townRepository;
    private final StatRepository statRepository;

    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository,
                             TeamRepository teamRepository,
                             TownRepository townRepository,
                             StatRepository statRepository,
                             ValidatorUtil validatorUtil,
                             XmlParser xmlParser,
                             ModelMapper modelMapper) {

        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.townRepository = townRepository;
        this.statRepository = statRepository;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() > 0;
    }

    @Override
    public String readPlayersFileContent() throws IOException {
        return Files.readString(XML_PLAYER_PATH);
    }

    @Override
    public String importPlayers() throws JAXBException, FileNotFoundException {
        final String xml = XML_PLAYER_PATH.toAbsolutePath().toString();

        final PlayersImportRootDto playersImportRootDto = this.xmlParser.convertFromFile(xml, PlayersImportRootDto.class);

        return playersImportRootDto.getPlayers()
                .stream()
                .map(this::importPlayer)
                .collect(Collectors.joining("\n"));
    }

    private String importPlayer(PlayersImportDto dto) {
        final boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_PLAYER_MESSAGE;
        }

        final Optional<Player> optionalPlayer = this.playerRepository.findByEmail(dto.getEmail());

        if (optionalPlayer.isPresent()) {
            return INVALID_PLAYER_MESSAGE;
        }

        final Optional<Team> optionalTeam = this.teamRepository.findByName(dto.getTeam().getName());
        final Optional<Town> optionalTown = this.townRepository.findByName(dto.getTown().getName());
        final Optional<Stat> optionalStat = this.statRepository.findById(dto.getStat().getId());

        final Player player = this.modelMapper.map(dto, Player.class);

        player.setTeam(optionalTeam.get());
        player.setTown(optionalTown.get());
        player.setStat(optionalStat.get());

        this.playerRepository.saveAndFlush(player);

        return SUCCESSFULLY_IMPORTED_PLAYER +
                player.getFirstName() + " " + player.getLastName() + " - " + player.getPosition().toString();
    }

    @Override
    public String exportBestPlayers() {
        final LocalDate after = LocalDate.of(1995, 1, 1);
        final LocalDate before = LocalDate.of(2003, 1, 1);
        final List<Player> bestPlayers =
                this.playerRepository.
                        findAllByBirthDateAfterAndBirthDateBeforeOrderByStatShootingDescStatPassingDescStatEnduranceDescLastNameAsc
                                (after, before);

        return bestPlayers.stream().map(Player::toString).collect(Collectors.joining("\n"));
    }

}


