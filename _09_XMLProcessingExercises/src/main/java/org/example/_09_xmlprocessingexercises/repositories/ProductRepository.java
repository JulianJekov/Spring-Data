package org.example._09_xmlprocessingexercises.repositories;

import org.example._09_xmlprocessingexercises.domain.dtos.product.*;
import org.example._09_xmlprocessingexercises.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new org.example._09_xmlprocessingexercises.domain.dtos.product.ProductsInRangeWithNoBuyerDto" +
            "(p.name, p.price, p.seller.firstName, p.seller.lastName) from Product p" +
            " where p.price > :low and p.price < :high and p.buyer is null order by p.price asc")
    List<ProductsInRangeWithNoBuyerDto>
    findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal low, BigDecimal high);
}
