package org.example._09_xmlprocessingexercises.services.Category;

import org.example._09_xmlprocessingexercises.domain.dtos.category.CategoryByProductsCountDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public interface CategoryService {
    List<CategoryByProductsCountDto> findAllByNumberOfProducts() throws IOException, JAXBException;
}
