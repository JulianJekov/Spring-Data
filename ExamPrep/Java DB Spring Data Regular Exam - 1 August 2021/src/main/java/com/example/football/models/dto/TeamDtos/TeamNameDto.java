package com.example.football.models.dto.TeamDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TeamNameDto {

    @XmlElement
    private String name;

    public String getName() {
        return name;
    }
}
