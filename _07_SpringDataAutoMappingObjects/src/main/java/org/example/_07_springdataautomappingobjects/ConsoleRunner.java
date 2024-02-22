package org.example._07_springdataautomappingobjects;

import org.example._07_springdataautomappingobjects.entities.Address;
import org.example._07_springdataautomappingobjects.entities.Employee;
import org.example._07_springdataautomappingobjects.entities.EmployeeDTO;
import org.example._07_springdataautomappingobjects.services.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@Component
public class ConsoleRunner implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public void run(String... args) throws Exception {

        //persist();
        ModelMapper modelMapper = new ModelMapper();
//        Employee manager = this.employeeService.findOneById(1).get();
//        EmployeeDTO managerDTO = modelMapper.map(manager, EmployeeDTO.class);
//        System.out.println(managerDTO);
        Employee employee = this.employeeService.findOneById(2).get();
        EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        System.out.println(employeeDTO);
    }

    private void persist() {
        Employee manager = new Employee(
                "Steve",
                "Jobbsen",
                BigDecimal.TEN,
                LocalDate.now(),
                true,
                null);

        Employee first = new Employee(
                "Stephen",
                "Bjorn",
                BigDecimal.valueOf(4300),
                LocalDate.now(),
                true,
                manager);

        Employee second = new Employee(
                "Kirilyc",
                "Lefi",
                BigDecimal.valueOf(4400),
                LocalDate.now(),
                true,
                manager);


        this.employeeService.save(first);
    }
}
