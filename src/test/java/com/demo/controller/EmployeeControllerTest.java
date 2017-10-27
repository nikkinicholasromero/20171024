package com.demo.controller;

import com.demo.controller.response.StandardResponse;
import com.demo.controller.response.Status;
import com.demo.domain.Employee;
import com.demo.orchestrator.EmployeeOrchestrator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {
    @Mock
    private EmployeeOrchestrator employeeOrchestrator;
    private EmployeeController employeeController;
    private MockMvc mockMvc;

    private Employee employee;
    private List<Employee> employees;
    private ObjectMapper mapper;

    @Before
    public void setup() {
        employeeController = new EmployeeController(employeeOrchestrator);
        mockMvc = standaloneSetup(employeeController).build();
        employee = new Employee();
        employee.setId("1");
        employee.setEmployeeId("2017-01");
        employee.setFirstName("Nikki Nicholas");
        employee.setMiddleName("Domingo");
        employee.setLastName("Romero");
        employee.setGender("Male");
        employee.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));

        employees = new ArrayList<>();

        Employee employee01 = new Employee();
        employee01.setId("1");
        employee01.setEmployeeId("2017-01");
        employee01.setFirstName("Nikki Nicholas");
        employee01.setMiddleName("Domingo");
        employee01.setLastName("Romero");
        employee01.setGender("Male");
        employee01.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee01.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee01.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employees.add(employee01);

        Employee employee02 = new Employee();
        employee02.setId("2");
        employee02.setEmployeeId("2017-02");
        employee02.setFirstName("Leslie Anne");
        employee02.setMiddleName("Suarez");
        employee02.setLastName("Romero");
        employee02.setGender("Female");
        employee02.setBirthDate(LocalDate.of(1992, Month.AUGUST, 21));
        employee02.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee02.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employees.add(employee02);

        Employee employee03 = new Employee();
        employee03.setId("3");
        employee03.setEmployeeId("2017-03");
        employee03.setFirstName("Maven Claire");
        employee03.setMiddleName("Sayin");
        employee03.setLastName("Romero");
        employee03.setGender("Female");
        employee03.setBirthDate(LocalDate.of(2016, Month.NOVEMBER, 24));
        employee03.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee03.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employees.add(employee03);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void findOne_whenResultIsEmpty_thenReturnEmpty() throws Exception {
        String id = "invalidId";
        StandardResponse<Employee> response = new StandardResponse<Employee>(Status.FAILED)
            .message("Employee does not exist")
            .errors(Arrays.asList("EMPLOYEE_DOES_NOT_EXIST"));
        when(employeeOrchestrator.findOne(id)).thenReturn(response);

        mockMvc.perform(get("/employee/" + id))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.FAILED.toString())))
            .andExpect(jsonPath("$.message", is("Employee does not exist")))
            .andExpect(jsonPath("$.payload").doesNotExist())
            .andExpect(jsonPath("$.errors.[0]", is("EMPLOYEE_DOES_NOT_EXIST")));
    }

    @Test
    public void findOne_whenResultIsNotEmpty_thenReturnEmployee() throws Exception {
        StandardResponse<Employee> response = new StandardResponse<Employee>(Status.SUCCESS)
            .payload(employee);
        when(employeeOrchestrator.findOne("1")).thenReturn(response);

        mockMvc.perform(get("/employee/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.payload.id", is(employee.getId())))
            .andExpect(jsonPath("$.payload.employeeId", is(employee.getEmployeeId())))
            .andExpect(jsonPath("$.payload.firstName", is(employee.getFirstName())))
            .andExpect(jsonPath("$.payload.middleName", is(employee.getMiddleName())))
            .andExpect(jsonPath("$.payload.lastName", is(employee.getLastName())))
            .andExpect(jsonPath("$.payload.gender", is(employee.getGender())))
            .andExpect(jsonPath("$.payload.birthDate", is(employee.getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.dateCreated", is(employee.getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.dateLastUpdated", is(employee.getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    public void findAll_whenResultIsEmpty_thenReturnEmpty() throws Exception {
        StandardResponse<List<Employee>> response = new StandardResponse<List<Employee>>(Status.SUCCESS)
            .payload(new ArrayList<>());
        when(employeeOrchestrator.findAll()).thenReturn(response);

        mockMvc.perform(get("/employee"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.payload.[*]", hasSize(0)))
            .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    public void findAll_whenResultIsNotEmpty_thenReturnList() throws Exception {
        StandardResponse<List<Employee>> response = new StandardResponse<List<Employee>>(Status.SUCCESS)
            .payload(employees);
        when(employeeOrchestrator.findAll()).thenReturn(response);

        mockMvc.perform(get("/employee"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message").doesNotExist())
            .andExpect(jsonPath("$.payload.[*]", hasSize(employees.size())))
            .andExpect(jsonPath("$.payload.[0].id", is(employees.get(0).getId())))
            .andExpect(jsonPath("$.payload.[0].employeeId", is(employees.get(0).getEmployeeId())))
            .andExpect(jsonPath("$.payload.[0].firstName", is(employees.get(0).getFirstName())))
            .andExpect(jsonPath("$.payload.[0].middleName", is(employees.get(0).getMiddleName())))
            .andExpect(jsonPath("$.payload.[0].lastName", is(employees.get(0).getLastName())))
            .andExpect(jsonPath("$.payload.[0].gender", is(employees.get(0).getGender())))
            .andExpect(jsonPath("$.payload.[0].birthDate", is(employees.get(0).getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[0].dateCreated", is(employees.get(0).getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[0].dateLastUpdated", is(employees.get(0).getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[1].id", is(employees.get(1).getId())))
            .andExpect(jsonPath("$.payload.[1].employeeId", is(employees.get(1).getEmployeeId())))
            .andExpect(jsonPath("$.payload.[1].firstName", is(employees.get(1).getFirstName())))
            .andExpect(jsonPath("$.payload.[1].middleName", is(employees.get(1).getMiddleName())))
            .andExpect(jsonPath("$.payload.[1].lastName", is(employees.get(1).getLastName())))
            .andExpect(jsonPath("$.payload.[1].gender", is(employees.get(1).getGender())))
            .andExpect(jsonPath("$.payload.[1].birthDate", is(employees.get(1).getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[1].dateCreated", is(employees.get(1).getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[1].dateLastUpdated", is(employees.get(1).getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[2].id", is(employees.get(2).getId())))
            .andExpect(jsonPath("$.payload.[2].employeeId", is(employees.get(2).getEmployeeId())))
            .andExpect(jsonPath("$.payload.[2].firstName", is(employees.get(2).getFirstName())))
            .andExpect(jsonPath("$.payload.[2].middleName", is(employees.get(2).getMiddleName())))
            .andExpect(jsonPath("$.payload.[2].lastName", is(employees.get(2).getLastName())))
            .andExpect(jsonPath("$.payload.[2].gender", is(employees.get(2).getGender())))
            .andExpect(jsonPath("$.payload.[2].birthDate", is(employees.get(2).getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[2].dateCreated", is(employees.get(2).getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[2].dateLastUpdated", is(employees.get(2).getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    public void create_whenValidEmployee() throws Exception {
        StandardResponse response = new StandardResponse(Status.SUCCESS)
            .message("Employee created");
        when(employeeOrchestrator.create(any(Employee.class))).thenReturn(response);

        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee created")))
            .andExpect(jsonPath("$.payload").doesNotExist())
            .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    public void update_whenValidEmployee() throws Exception {
        StandardResponse response = new StandardResponse(Status.SUCCESS)
            .message("Employee updated");
        when(employeeOrchestrator.update(any(Employee.class))).thenReturn(response);

        mockMvc.perform(patch("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee updated")))
            .andExpect(jsonPath("$.payload").doesNotExist())
            .andExpect(jsonPath("$.errors").doesNotExist());
    }

    @Test
    public void delete_whenValidEmployee() throws Exception {
        StandardResponse response = new StandardResponse(Status.SUCCESS)
            .message("Employee deleted");
        when(employeeOrchestrator.delete(any(Employee.class))).thenReturn(response);

        mockMvc.perform(delete("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee deleted")))
            .andExpect(jsonPath("$.payload").doesNotExist())
            .andExpect(jsonPath("$.errors").doesNotExist());
    }
}
