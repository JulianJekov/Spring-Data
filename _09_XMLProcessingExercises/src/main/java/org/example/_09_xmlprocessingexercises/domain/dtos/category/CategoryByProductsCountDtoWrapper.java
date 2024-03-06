package org.example._09_xmlprocessingexercises.domain.dtos.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryByProductsCountDtoWrapper {

    @XmlElement(name = "category")
    private List<CategoryByProductsCountDto> categories;

    public CategoryByProductsCountDtoWrapper() {
    }

    public List<CategoryByProductsCountDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryByProductsCountDto> categories) {
        this.categories = categories;
    }
}
