package com.demo.controller;

import com.demo.domain.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public Employee findOne(@PathVariable("id") String id) {
        return employeeService.findOne(id);
    }

    @GetMapping("")
    public List<Employee> findAll() {
        return employeeService.findAll();
    }

    @PutMapping("")
    public void create(@RequestBody Employee employee) {
        employeeService.create(employee);
    }

    @PatchMapping("")
    public void update(@RequestBody Employee employee) {
        employeeService.update(employee);
    }

    @DeleteMapping("")
    public void delete(@RequestBody Employee employee) {
        employeeService.delete(employee);
    }
}
