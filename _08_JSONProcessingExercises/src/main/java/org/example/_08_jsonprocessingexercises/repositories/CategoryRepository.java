package org.example._08_jsonprocessingexercises.repositories;

import org.example._08_jsonprocessingexercises.domain.dtos.category.CategoryByProductsCountDto;
import org.example._08_jsonprocessingexercises.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

//    @Query(value = "select * from products_shop.categories order by rand() limit :rand",
//            nativeQuery = true)
//    Set<Category> getRandomEntity(long rand);


    @Query("select new org.example._08_jsonprocessingexercises.domain.dtos.category.CategoryByProductsCountDto" +
            "(c.name, count(p.id), avg (p.price), sum(p.price))" +
            "from Product p join p.categories c group by c.id order by count (p.id)")
    List<CategoryByProductsCountDto> findAllByProductsCount();
}
