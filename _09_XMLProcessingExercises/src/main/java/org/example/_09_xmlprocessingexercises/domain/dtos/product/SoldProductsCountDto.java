package org.example._09_xmlprocessingexercises.domain.dtos.product;

import javax.xml.bind.annotation.*;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductsCountDto {

    @XmlAttribute(name = "count")
    private Integer count;

    @XmlElement(name = "product")
    private Set<ProductSoldInfoDto> products;

    public SoldProductsCountDto() {
        this.products = new HashSet<>();
    }

    public SoldProductsCountDto(Set<ProductSoldInfoDto> products) {
        this.products = products;
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
