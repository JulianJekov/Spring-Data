package org.example._09_xmlprocessingexercises.services.Category;

import org.example._09_xmlprocessingexercises.domain.dtos.category.CategoryByProductsCountDto;
import org.example._09_xmlprocessingexercises.domain.dtos.category.CategoryByProductsCountDtoWrapper;
import org.example._09_xmlprocessingexercises.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

import static org.example._09_xmlprocessingexercises.constants.Paths.CATEGORIES_BY_PRODUCTS_COUNT_XML_PATH;
import static org.example._09_xmlprocessingexercises.constants.Utils.writeIntoXmlFile;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryByProductsCountDto> findAllByNumberOfProducts() throws IOException, JAXBException {
        final List<CategoryByProductsCountDto> categories = this.categoryRepository.findAllByProductsCount();

        final CategoryByProductsCountDtoWrapper categoryByProductsCountDtoWrapper = new CategoryByProductsCountDtoWrapper();
        categoryByProductsCountDtoWrapper.setCategories(categories);

        writeIntoXmlFile(categoryByProductsCountDtoWrapper, CATEGORIES_BY_PRODUCTS_COUNT_XML_PATH);

        return categories;
    }
}
