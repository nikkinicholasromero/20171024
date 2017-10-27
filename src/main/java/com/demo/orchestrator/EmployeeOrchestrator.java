package com.demo.orchestrator;

import com.demo.controller.response.StandardResponse;
import com.demo.controller.response.Status;
import com.demo.domain.Employee;
import com.demo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class EmployeeOrchestrator {
    private EmployeeService employeeService;

    @Autowired
    public EmployeeOrchestrator(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public StandardResponse<Employee> findOne(String id) {
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

        return response;
    }

    public StandardResponse<List<Employee>> findAll() {
        List<Employee> employees = employeeService.findAll();
        return new StandardResponse<List<Employee>>(Status.SUCCESS)
                .payload(employees);
    }

    public StandardResponse create(Employee employee) {
        employeeService.create(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee created");
        return response;
    }

    public StandardResponse update(Employee employee) {
        employeeService.update(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee updated");
        return response;
    }

    public StandardResponse delete(Employee employee) {
        employeeService.delete(employee);
        StandardResponse response = new StandardResponse(Status.SUCCESS)
                .message("Employee deleted");
        return response;
    }
}
