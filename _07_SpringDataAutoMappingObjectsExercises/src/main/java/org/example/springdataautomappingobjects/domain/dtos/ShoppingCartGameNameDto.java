package org.example.springdataautomappingobjects.domain.dtos;

public class ShoppingCartGameNameDto {

    private String title;

    public ShoppingCartGameNameDto(String title) {
        this.title = title;
    }

    public ShoppingCartGameNameDto() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
