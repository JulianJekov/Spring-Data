package org.example._09_xmlprocessingexercises.services.Product;

import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsInRangeNoBuyerWrapper;
import org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsInRangeWithNoBuyerDto;
import org.example._09_xmlprocessingexercises.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import static org.example._09_xmlprocessingexercises.constants.Paths.PRODUCTS_IN_RANGE_XML_PATH;
import static org.example._09_xmlprocessingexercises.constants.Utils.writeIntoXmlFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductsInRangeWithNoBuyerDto> getProductsInRangeWithNoBuyer(float low, float high) throws JAXBException {
        final BigDecimal lowPrice = BigDecimal.valueOf(low);
        final BigDecimal highPrice = BigDecimal.valueOf(high);

        final List<ProductsInRangeWithNoBuyerDto> products =
                this.productRepository.findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(lowPrice, highPrice);

        final ProductsInRangeNoBuyerWrapper productsInRangeNoBuyerWrapper = new ProductsInRangeNoBuyerWrapper();
        productsInRangeNoBuyerWrapper.setProducts(products);

        writeIntoXmlFile(productsInRangeNoBuyerWrapper, PRODUCTS_IN_RANGE_XML_PATH);

        return products;
    }
}
