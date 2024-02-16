package com.example.advquerying.services;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ShampooServiceImpl implements ShampooService {


    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> selectShampoosBySize(Size size) {
        return this.shampooRepository.findAllBySize(size);
    }

    @Override
    public List<Shampoo> selectShampoosBySizeOrLabel(Size size, long labelId) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPriceAsc(size, labelId);
    }

    @Override
    public List<Shampoo> selectShampoosByPriceHigherThan(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public int countAllWithPriceLowerThan(BigDecimal price) {
        return this.shampooRepository.countByPriceLessThan(price);
    }

    @Override
    public List<Shampoo> findAllByIngredientsNames(Set<String> ingredientsNames) {
        return shampooRepository.findByIngredientsNames(ingredientsNames);
    }

    @Override
    public List<Shampoo> selectAllWithIngredientsLessThan(int count) {
        return shampooRepository.findAllByIngredientCountBiggerThan(count);
    }

}
