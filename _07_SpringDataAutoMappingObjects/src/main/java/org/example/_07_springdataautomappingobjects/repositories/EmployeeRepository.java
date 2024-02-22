package org.example._07_springdataautomappingobjects.repositories;

import org.example._07_springdataautomappingobjects.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
