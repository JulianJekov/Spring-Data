package com.example.football.models.dto.StatDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class StatIdDto {

    @XmlElement
    private long id;

    public long getId() {
        return id;
    }
}
