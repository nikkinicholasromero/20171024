package com.demo.controller;

import com.demo.controller.response.StandardResponse;
import com.demo.domain.Employee;
import com.demo.orchestrator.EmployeeOrchestrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeOrchestrator employeeOrchestrator;

    @Autowired
    public EmployeeController(EmployeeOrchestrator employeeOrchestrator) {
        this.employeeOrchestrator = employeeOrchestrator;
    }

    @GetMapping("/{id}")
    public HttpEntity<StandardResponse<Employee>> findOne(@PathVariable("id") String id) {
        return new ResponseEntity<>(employeeOrchestrator.findOne(id), HttpStatus.OK);
    }

    @GetMapping("")
    public HttpEntity<StandardResponse<List<Employee>>> findAll() {
        return new ResponseEntity<>(employeeOrchestrator.findAll(), HttpStatus.OK);
    }

    @PutMapping("")
    public HttpEntity<StandardResponse> create(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeOrchestrator.create(employee), HttpStatus.OK);
    }

    @PatchMapping("")
    public HttpEntity<StandardResponse> update(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeOrchestrator.update(employee), HttpStatus.OK);
    }

    @DeleteMapping("")
    public HttpEntity<StandardResponse> delete(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeOrchestrator.delete(employee), HttpStatus.OK);
    }
}
