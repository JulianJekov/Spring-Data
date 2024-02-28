package org.example._08_jsonprocessingexercises.repositories;


import org.example._08_jsonprocessingexercises.domain.dtos.user.UserWithSoldProductsCountDto;
import org.example._08_jsonprocessingexercises.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    @Query(value = "select * from products_shop.users order by rand() limit 1", nativeQuery = true)
//    User getRandomEntity();

    @Query("select u from User u inner join u.soldProducts p where p.buyer is not null order by u.lastName, u.firstName")
    List<User> findAllWithSoldProducts();

    @Query("select u from User u join u.soldProducts p where p.buyer is not null order by size(u.soldProducts) desc , u.lastName asc")
    List<User> findAllBySoldProductsCount();
}
