package com.demo.controller;

import com.demo.controller.response.StandardResponse;
import com.demo.controller.response.Status;
import com.demo.domain.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/{id}")
    public HttpEntity<StandardResponse<Employee>> findOne(@PathVariable("id") String id) {
        StandardResponse<Employee> response;

        Employee employee = employeeService.findOne(id);
        if (employee != null) {
            response = new StandardResponse<Employee>(Status.SUCCESS)
                .payload(employee);
        } else {
            response = new StandardResponse<Employee>(Status.FAILED)
                .message("Employee does not exist")
                .errors(Arrays.asList("EMPLOYEE_DOES_NOT_EXIST"));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public HttpEntity<StandardResponse<List<Employee>>> findAll() {
        StandardResponse<List<Employee>> response;

        List<Employee> employees = employeeService.findAll();
        response = new StandardResponse<List<Employee>>(Status.SUCCESS)
                .payload(employees);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("")
    public HttpEntity<StandardResponse> create(@RequestBody Employee employee) {
        employeeService.create(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("")
    public HttpEntity<StandardResponse> update(@RequestBody Employee employee) {
        employeeService.update(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee updated");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("")
    public HttpEntity<StandardResponse> delete(@RequestBody Employee employee) {
        employeeService.delete(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
