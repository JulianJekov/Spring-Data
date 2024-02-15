package org.example._05_springdataintroexercises.repositories;

import org.example._05_springdataintroexercises.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
