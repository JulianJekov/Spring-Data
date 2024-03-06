package org.example._09_xmlprocessingexercises.domain.dtos.user;

import org.example._09_xmlprocessingexercises.domain.dtos.product.SoldProductsCountDto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsInfoDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlAttribute(name = "age")
    private Integer age;

    @XmlElement(name = "sold-products")
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
