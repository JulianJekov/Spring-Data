package org.example._09_xmlprocessingexercises.services.Product;

import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsInRangeWithNoBuyerDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductsInRangeWithNoBuyerDto> getProductsInRangeWithNoBuyer(float low, float high) throws IOException, JAXBException;
}
