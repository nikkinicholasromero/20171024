package com.demo.service;

import com.demo.domain.Employee;
import com.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findOne(String id) {
        return employeeRepository.findOne(id);
    }

    public List<Employee> findAll() {
        List<Employee> employees = new ArrayList<>();
        employeeRepository.findAll().forEach(employees::add);
        return employees;
    }

    public void create(Employee employee) {
        employee.setDateCreated(LocalDateTime.now());
        employee.setDateLastUpdated(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    public void update(Employee employee) {
        employee.setDateLastUpdated(LocalDateTime.now());
        employeeRepository.save(employee);
    }

    public void delete(Employee employee) {
        employee.setDateLastUpdated(LocalDateTime.now());
        employeeRepository.delete(employee);
    }
}
