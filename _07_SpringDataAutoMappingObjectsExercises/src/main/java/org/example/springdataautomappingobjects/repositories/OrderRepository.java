package org.example.springdataautomappingobjects.repositories;

import org.example.springdataautomappingobjects.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
