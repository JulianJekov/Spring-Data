package org.example._08_jsonprocessingexercises.domain.dtos.category;

public class CategoryImportDto {
    private String name;

    public CategoryImportDto() {
    }

    public CategoryImportDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
