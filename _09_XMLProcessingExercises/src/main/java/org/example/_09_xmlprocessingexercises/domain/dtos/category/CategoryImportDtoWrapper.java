package org.example._09_xmlprocessingexercises.domain.dtos.category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryImportDtoWrapper {

    @XmlElement(name = "category")
    private List<CategoryImportDto> categories;

    public CategoryImportDtoWrapper() {
    }

    public List<CategoryImportDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryImportDto> categories) {
        this.categories = categories;
    }
}
