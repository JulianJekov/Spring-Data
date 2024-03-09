package com.example.football.models.dto.TeamDtos;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class TeamsImportDto {

    @Size(min = 3)
    private String name;

    @Size(min = 3)
    private String stadiumName;

    @Min(1000)
    private int fanBase;

    @Size(min = 10)
    private String history;

    private String townName;

    public TeamsImportDto() {
    }

    public String getName() {
        return name;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public int getFanBase() {
        return fanBase;
    }

    public String getHistory() {
        return history;
    }

    public String getTownName() {
        return townName;
    }
}
