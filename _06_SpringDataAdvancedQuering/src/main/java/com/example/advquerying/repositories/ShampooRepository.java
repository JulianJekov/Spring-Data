package com.example.advquerying.repositories;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ShampooRepository extends JpaRepository<Shampoo, Long> {

    List<Shampoo> findAllByBrand(String brand);

    List<Shampoo> findAllByBrandAndSize(String brand, Size size);

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySize(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPriceAsc(Size size, long labelId);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    int countByPriceLessThan(BigDecimal price);


    @Query("select s from Shampoo s join s.ingredients i where i.name in :ingredientsNames")
    List<Shampoo> findByIngredientsNames(Set<String> ingredientsNames);

    @Query("select s from Shampoo s where s.ingredients.size < :count")
    List<Shampoo> findAllByIngredientCountBiggerThan(int count);
}
