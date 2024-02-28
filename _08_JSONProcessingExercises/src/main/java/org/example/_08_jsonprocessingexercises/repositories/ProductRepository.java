package org.example._08_jsonprocessingexercises.repositories;

import org.example._08_jsonprocessingexercises.domain.dtos.product.ProductInRangeWithNoBuyerDto;
import org.example._08_jsonprocessingexercises.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new org.example._08_jsonprocessingexercises.domain.dtos.product.ProductInRangeWithNoBuyerDto" +
            "(p.name, p.price, p.seller.firstName, p.seller.lastName) from Product p" +
            " where p.price > :low and p.price < :high and p.buyer is null order by p.price asc")
    List<ProductInRangeWithNoBuyerDto>
    findAllByPriceBetweenAndBuyerIsNullOrderByPriceAsc(BigDecimal low, BigDecimal high);
}
