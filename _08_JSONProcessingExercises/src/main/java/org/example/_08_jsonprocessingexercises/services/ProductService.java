package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductInRangeWithNoBuyerDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductInRangeWithNoBuyerDto> getProductsInRangeWithNoBuyer(float low, float high) throws IOException;
}
