package org.example._08_jsonprocessingexercises.domain.dtos.user;

import java.math.BigDecimal;

public class ProductSoldInfoDto {

    private  String name;

    private BigDecimal price;

    public ProductSoldInfoDto() {
    }

    public ProductSoldInfoDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
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
}
