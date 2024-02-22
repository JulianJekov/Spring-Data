package org.example._07_springdataautomappingobjects.services;

import org.example._07_springdataautomappingobjects.entities.Employee;

import java.util.Optional;

public interface EmployeeService {

    Optional<Employee> findOneById(int id);

    void save (Employee employee);
}
