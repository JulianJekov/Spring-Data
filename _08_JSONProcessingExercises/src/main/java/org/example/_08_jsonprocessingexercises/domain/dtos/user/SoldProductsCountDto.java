package org.example._08_jsonprocessingexercises.domain.dtos.user;

import java.util.HashSet;
import java.util.Set;

public class SoldProductsCountDto {

    private Integer count;

    private Set<ProductSoldInfoDto> products;

    public SoldProductsCountDto() {
        this.products = new HashSet<>();
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ProductSoldInfoDto> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductSoldInfoDto> products) {
        this.products = products;
    }
}
