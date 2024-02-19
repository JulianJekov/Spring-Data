package org.example._07_springdataautomappingobjects;

import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        Address address = new Address("street 1", "varna");

        Employee manager = new Employee(
                "Steve",
                "Jobbsen",
                BigDecimal.TEN,
                LocalDate.now(),
                address,
                true);

        Employee first = new Employee(
                "Stephen",
                "Bjorn",
                BigDecimal.valueOf(4300),
                LocalDate.now(),
                address,
                true);

        Employee second = new Employee(
                "Kirilyc",
                "Lefi",
                BigDecimal.valueOf(4400),
                LocalDate.now(),
                address,
                true);

        manager.addEmployees(first);
        manager.addEmployees(second);

        ModelMapper modelMapper = new ModelMapper();

        ManagerDTO managerDTO = modelMapper.map(manager, ManagerDTO.class);

        System.out.println(managerDTO);
    }
}
