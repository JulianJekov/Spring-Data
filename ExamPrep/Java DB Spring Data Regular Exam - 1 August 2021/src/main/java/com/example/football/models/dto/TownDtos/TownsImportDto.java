package com.example.football.models.dto.TownDtos;


import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class TownsImportDto {

    @Size(min = 2)
    private String name;

    @Positive
    private int population;

    @Size(min = 10)
    private String travelGuide;

    public TownsImportDto() {
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public String getTravelGuide() {
        return travelGuide;
    }
}
