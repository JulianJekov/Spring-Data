package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {
    List<Ingredient> selectIngredientsByNameStartWith(String name);

    List<Ingredient> selectIngredientsByNameInAGivenList(List<String> ingredientsNames);

    void deleteByName(String name);

    void increasePriceByPercent(BigDecimal percent);

    void increasePriceByNames(List<String> names, BigDecimal percent);
}
