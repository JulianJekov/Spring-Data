package org.example._05_springdataintroexercises.services;

import org.example._05_springdataintroexercises.entities.Category;

import java.util.Set;

public interface CategoryService {
    Set<Category> getRandomCategories();
}
