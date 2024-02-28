package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.domain.dtos.category.CategoryByProductsCountDto;
import org.example._08_jsonprocessingexercises.domain.entities.Category;
import org.example._08_jsonprocessingexercises.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.example._08_jsonprocessingexercises.constants.Paths.CATEGORIES_BY_PRODUCTS_COUNT_JSON_PATH;
import static org.example._08_jsonprocessingexercises.constants.Utils.writeIntoJsonFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryByProductsCountDto> findAllByNumberOfProducts() throws IOException {
        List<CategoryByProductsCountDto> categories = this.categoryRepository.findAllByProductsCount();

        writeIntoJsonFile(categories, CATEGORIES_BY_PRODUCTS_COUNT_JSON_PATH);

        return categories;
    }
}
