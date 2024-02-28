package org.example._08_jsonprocessingexercises.services;

import org.example._08_jsonprocessingexercises.domain.dtos.category.CategoryByProductsCountDto;
import org.example._08_jsonprocessingexercises.domain.entities.Category;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryByProductsCountDto> findAllByNumberOfProducts() throws IOException;
}
