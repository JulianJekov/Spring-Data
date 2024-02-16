package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> selectIngredientsByNameStartWith(String name) {
        return ingredientRepository.findAllByNameStartingWith(name);
    }

    @Override
    public List<Ingredient> selectIngredientsByNameInAGivenList(List<String> ingredientsNames) {
        return ingredientRepository.findAllByNameInOrderByPriceAsc(ingredientsNames);
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        ingredientRepository.deleteByName(name);
    }

    @Override
    @Transactional
    public void increasePriceByPercent(BigDecimal percent) {
        ingredientRepository.increasePriceByPercent(percent);
    }

    @Override
    @Transactional
    public void increasePriceByNames(List<String> names, BigDecimal percent) {
        ingredientRepository.increasePriceByNames(names, percent);
    }
}
