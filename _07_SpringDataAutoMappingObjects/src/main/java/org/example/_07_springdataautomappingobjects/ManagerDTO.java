package org.example._07_springdataautomappingobjects;

import java.util.Set;
import java.util.stream.Collectors;

public class ManagerDTO {
    private String firstName;
    private String lastName;
    private Set<EmployeeDTO> employees;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<EmployeeDTO> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<EmployeeDTO> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {

        String employeesToString = this.employees
                .stream()
                .map(EmployeeDTO::toString)
                .map(s -> "    - " + s)
                .collect(Collectors.joining("\n"));

        return String.format("%s %s | Employees: %d%n%s", this.firstName,
                this.lastName,
                this.employees.size(),
                employeesToString);
    }
}
