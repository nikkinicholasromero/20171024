package com.demo.repository;

import com.demo.domain.Employee;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    public Employee findOne(String id) {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeId("2017-0001");
        employee.setFirstName("Nikki Nicholas");
        employee.setMiddleName("Domingo");
        employee.setLastName("Romero");
        employee.setGender("Male");
        employee.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee.setDateCreated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));
        employee.setDateLastUpdated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));
        return employee;
    }

    public Iterable<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        Employee employee01 = new Employee();
        employee01.setId("1");
        employee01.setEmployeeId("2017-0001");
        employee01.setFirstName("Nikki Nicholas");
        employee01.setMiddleName("Domingo");
        employee01.setLastName("Romero");
        employee01.setGender("Male");
        employee01.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee01.setDateCreated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));
        employee01.setDateLastUpdated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));

        Employee employee02 = new Employee();
        employee02.setId("2");
        employee02.setEmployeeId("2017-0002");
        employee02.setFirstName("Leslie Anne");
        employee02.setMiddleName("Suarez");
        employee02.setLastName("Sayin");
        employee02.setGender("Female");
        employee02.setBirthDate(LocalDate.of(1992, Month.AUGUST, 21));
        employee02.setDateCreated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));
        employee02.setDateLastUpdated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));

        Employee employee03 = new Employee();
        employee03.setId("3");
        employee03.setEmployeeId("2017-0003");
        employee03.setFirstName("Maven Claire");
        employee03.setMiddleName("Sayin");
        employee03.setLastName("Romero");
        employee03.setGender("Female");
        employee03.setBirthDate(LocalDate.of(2016, Month.NOVEMBER, 24));
        employee03.setDateCreated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));
        employee03.setDateLastUpdated(LocalDateTime.of(1991, Month.AUGUST, 5, 1, 17, 2, 13));

        employees.add(employee01);
        employees.add(employee02);
        employees.add(employee03);

        return () -> employees.iterator();
    }

    public Employee save(Employee employee) {
        return employee;
    }

    public void delete(Employee employee) {
    }
}
