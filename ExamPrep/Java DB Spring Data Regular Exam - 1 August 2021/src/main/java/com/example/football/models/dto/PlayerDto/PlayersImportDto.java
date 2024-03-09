package com.example.football.models.dto.PlayerDto;

import com.example.football.models.dto.StatDto.StatIdDto;
import com.example.football.models.dto.TeamDtos.TeamNameDto;
import com.example.football.models.dto.TownDtos.TownNameDto;
import com.example.football.models.entity.enums.PlayerPosition;
import com.example.football.util.LocalDateAdapter;


import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class PlayersImportDto {

    @XmlElement(name = "first-name")
    @Size(min = 2)
    private String firstName;

    @XmlElement(name = "last-name")
    @Size(min = 2)
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement(name = "birth-date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate birthDate;

    @XmlElement
    private PlayerPosition position;

    @XmlElement(name = "town")
    private TownNameDto town;

    @XmlElement(name = "team")
    private TeamNameDto team;

    @XmlElement(name = "stat")
    private StatIdDto stat;

    public PlayersImportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public TownNameDto getTown() {
        return town;
    }

    public TeamNameDto getTeam() {
        return team;
    }

    public StatIdDto getStat() {
        return stat;
    }
}
