package org.example._08_jsonprocessingexercises.domain.dtos.user;

public class UserWithSoldProductsInfoDto {

    private String firstName;

    private String lastName;

    private Integer age;

    private SoldProductsCountDto soldProducts;

    public UserWithSoldProductsInfoDto() {
    }

    public UserWithSoldProductsInfoDto(String firstName, String lastName, Integer age, SoldProductsCountDto soldProducts) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public SoldProductsCountDto getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(SoldProductsCountDto soldProducts) {
        this.soldProducts = soldProducts;
    }
}
