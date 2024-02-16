package com.example.advquerying.repositories;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByNameStartingWith(String name);

    List<Ingredient> findAllByNameInOrderByPriceAsc(List<String> ingredientsNames);

    void deleteByName(String name);

    @Query("update Ingredient i set i.price = i.price + (i.price * :percent)")
    @Modifying
    void increasePriceByPercent(BigDecimal percent);

    @Query("update Ingredient i set i.price = i.price + (i.price * :percent) where i.name in :names")
    @Modifying
    void increasePriceByNames(List<String> names, BigDecimal percent);
}
