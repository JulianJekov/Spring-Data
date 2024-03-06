package org.example._09_xmlprocessingexercises.domain.dtos.product;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsInRangeNoBuyerWrapper {

    @XmlElement(name = "product")
    private List<ProductsInRangeWithNoBuyerDto> products;

    public ProductsInRangeNoBuyerWrapper() {
    }


    public List<ProductsInRangeWithNoBuyerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsInRangeWithNoBuyerDto> products) {
        this.products = products;
    }
}
