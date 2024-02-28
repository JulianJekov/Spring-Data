package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.constants.Utils;
import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductInRangeWithNoBuyerDto;
import org.example._08_jsonprocessingexercises.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.example._08_jsonprocessingexercises.constants.Paths.PRODUCTS_IN_RANGE_JSON_PATH;
import static org.example._08_jsonprocessingexercises.constants.Utils.writeIntoJsonFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductInRangeWithNoBuyerDto> getProductsInRangeWithNoBuyer(float low, float high) throws IOException {
        BigDecimal lowPrice = BigDecimal.valueOf(low);
        BigDecimal highPrice = BigDecimal.valueOf(high);

        List<ProductInRangeWithNoBuyerDto> products =
                this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lowPrice, highPrice);

        writeIntoJsonFile(products, PRODUCTS_IN_RANGE_JSON_PATH);
        return products;
    }
}
