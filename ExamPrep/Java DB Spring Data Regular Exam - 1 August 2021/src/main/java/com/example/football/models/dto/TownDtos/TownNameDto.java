package com.example.football.models.dto.TownDtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TownNameDto {

    @XmlElement
    private String name;

    public String getName() {
        return name;
    }
}
