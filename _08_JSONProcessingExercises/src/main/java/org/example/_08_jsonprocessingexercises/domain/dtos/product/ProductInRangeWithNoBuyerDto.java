package org.example._08_jsonprocessingexercises.domain.dtos.product;

import java.math.BigDecimal;

public class ProductInRangeWithNoBuyerDto {

    private String name;

    private BigDecimal price;

    private String seller;

    public ProductInRangeWithNoBuyerDto() {
    }

    public ProductInRangeWithNoBuyerDto(String name, BigDecimal price, String firstName, String lastName) {
        this.name = name;
        this.price = price;
        this.seller = firstName + " " + lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
