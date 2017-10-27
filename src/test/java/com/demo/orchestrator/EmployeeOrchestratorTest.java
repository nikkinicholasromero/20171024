package com.demo.orchestrator;

import com.demo.controller.response.StandardResponse;
import com.demo.controller.response.Status;
import com.demo.domain.Employee;
import com.demo.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeOrchestratorTest {
    private EmployeeOrchestrator employeeOrchestrator;

    @Mock
    private EmployeeService employeeService;
    @Mock
    private Employee employee;
    private List<Employee> employees;

    @Before
    public void setup() {
        employeeOrchestrator = new EmployeeOrchestrator(employeeService);
        employees = new ArrayList<>();

        Employee employee01 = new Employee();
        employee01.setId("01");
        employee01.setEmployeeId("2017-01");
        employee01.setFirstName("Nikki Nicholas");
        employee01.setMiddleName("Domingo");
        employee01.setLastName("Romero");
        employee01.setGender("Male");
        employee01.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee01.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employee01.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employees.add(employee01);

        Employee employee02 = new Employee();
        employee02.setId("02");
        employee02.setEmployeeId("2017-02");
        employee02.setFirstName("Leslie Anne");
        employee02.setMiddleName("Suarez");
        employee02.setLastName("Romero");
        employee02.setGender("Female");
        employee02.setBirthDate(LocalDate.of(1992, Month.AUGUST, 21));
        employee02.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employee02.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employees.add(employee02);

        Employee employee03 = new Employee();
        employee03.setId("03");
        employee03.setEmployeeId("2017-03");
        employee03.setFirstName("Maven Claire");
        employee03.setMiddleName("Sayin");
        employee03.setLastName("Romero");
        employee03.setGender("Female");
        employee03.setBirthDate(LocalDate.of(2016, Month.NOVEMBER, 24));
        employee03.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employee03.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 1, 0, 0));
        employees.add(employee03);
    }

    @Test
    public void findOne_whenResultIsEmpty_thenReturnEmpty() {
        String id = "invalidId";
        when(employeeService.findOne(id)).thenReturn(null);

        StandardResponse<Employee> actual = employeeOrchestrator.findOne(id);

        assertThat(actual.getStatus()).isEqualTo(Status.FAILED);
        assertThat(actual.getMessage()).isEqualTo("Employee does not exist");
        assertThat(actual.getErrors()).isEqualTo(Arrays.asList("EMPLOYEE_DOES_NOT_EXIST"));
        assertThat(actual.getPayload()).isNull();
    }

    @Test
    public void findOne_whenValidId_thenReturnEmployee() {
        String id = "1";
        when(employeeService.findOne(id)).thenReturn(employee);

        StandardResponse<Employee> actual = employeeOrchestrator.findOne(id);

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isNull();
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isEqualTo(employee);
    }

    @Test
    public void findAll_whenEmployeesIterableNotNull_thenReturnEmployeeList() {
        StandardResponse<List<Employee>> actual = employeeOrchestrator.findAll();

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isNull();
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isEqualTo(new ArrayList<>());
    }

    @Test
    public void findAll_whenEmployeesIterableNull_thenReturnEmptyList() {
        when(employeeService.findAll()).thenReturn(employees);

        StandardResponse<List<Employee>> actual = employeeOrchestrator.findAll();

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isNull();
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isEqualTo(employees);
    }

    @Test
    public void create_whenValidEmployee() {
        StandardResponse actual = employeeOrchestrator.create(employee);

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isEqualTo("Employee created");
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isNull();
    }

    @Test
    public void update_whenValidEmployee() {
        StandardResponse actual = employeeOrchestrator.update(employee);

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isEqualTo("Employee updated");
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isNull();
    }

    @Test
    public void delete_whenValidEmployee() {
        StandardResponse actual = employeeOrchestrator.delete(employee);

        assertThat(actual.getStatus()).isEqualTo(Status.SUCCESS);
        assertThat(actual.getMessage()).isEqualTo("Employee deleted");
        assertThat(actual.getErrors()).isNull();
        assertThat(actual.getPayload()).isNull();
    }
}
