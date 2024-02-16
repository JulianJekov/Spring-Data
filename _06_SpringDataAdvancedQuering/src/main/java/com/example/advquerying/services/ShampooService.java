package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public interface ShampooService {
    List<Shampoo> selectShampoosBySize(Size size);

    List<Shampoo> selectShampoosBySizeOrLabel(Size size, long labelId);

    List<Shampoo> selectShampoosByPriceHigherThan(BigDecimal price);

    int countAllWithPriceLowerThan(BigDecimal bigDecimal);

    List<Shampoo> findAllByIngredientsNames(Set<String> ingredientsNames);

    List<Shampoo> selectAllWithIngredientsLessThan(int count);
}
