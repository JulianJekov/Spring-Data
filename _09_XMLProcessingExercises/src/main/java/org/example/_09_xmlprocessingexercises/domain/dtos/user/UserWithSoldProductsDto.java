package org.example._09_xmlprocessingexercises.domain.dtos.user;

import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsSoldDtoWrapper;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserWithSoldProductsDto {

    @XmlAttribute(name = "first-name")
    private String firstName;

    @XmlAttribute(name = "last-name")
    private String lastName;

    @XmlElement(name = "sold-products")
    private ProductsSoldDtoWrapper soldProducts;

    public UserWithSoldProductsDto() {
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

    public ProductsSoldDtoWrapper getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(ProductsSoldDtoWrapper soldProducts) {
        this.soldProducts = soldProducts;
    }
}
