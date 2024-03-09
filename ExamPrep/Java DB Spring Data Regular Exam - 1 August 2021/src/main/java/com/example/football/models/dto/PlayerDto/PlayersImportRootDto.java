package com.example.football.models.dto.PlayerDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "players")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersImportRootDto {

    @XmlElement(name = "player")
    private List<PlayersImportDto> players;

    public List<PlayersImportDto> getPlayers() {
        return players;
    }
}
