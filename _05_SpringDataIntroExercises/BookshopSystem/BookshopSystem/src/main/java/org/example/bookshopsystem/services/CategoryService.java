package org.example.bookshopsystem.services;



import org.example.bookshopsystem.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
