package org.example._09_xmlprocessingexercises.domain.dtos.product;

import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsSoldDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsSoldDtoWrapper {

    @XmlElement(name = "product")
    private List<ProductsSoldDto> products;

    public ProductsSoldDtoWrapper() {
    }

    public List<ProductsSoldDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsSoldDto> products) {
        this.products = products;
    }
}
