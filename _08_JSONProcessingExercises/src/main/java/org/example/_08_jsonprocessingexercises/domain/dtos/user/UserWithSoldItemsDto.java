package org.example._08_jsonprocessingexercises.domain.dtos.user;

import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductsSoldDto;

import java.util.List;

public class UserWithSoldItemsDto {

    private String firstName;

    private String lastName;

    private List<ProductsSoldDto> soldProducts;

    public UserWithSoldItemsDto() {
    }

    public UserWithSoldItemsDto(String firstName, String lastName, List<ProductsSoldDto> soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.soldProducts = soldProducts;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductsSoldDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<ProductsSoldDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
