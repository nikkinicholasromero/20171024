package com.demo.controller;

import com.demo.controller.response.Status;
import com.demo.domain.Employee;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class EmployeeControllerFeatureTest {
    @Autowired
    private Flyway flyway;
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setup() throws Exception {
        flyway.migrate();

        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @After
    public void teardown() {
        flyway.clean();
    }

    @Test
    public void findOne_whenResultIsEmpty_thenReturnEmpty() throws Exception {
        mockMvc.perform(get("/employee/invalidId"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.FAILED.toString())))
            .andExpect(jsonPath("$.errors.[0]", is("EMPLOYEE_DOES_NOT_EXIST")))
            .andExpect(jsonPath("$.message", is("Employee does not exist")));
    }

    @Test
    public void findOne_whenResultIsNotEmpty_thenReturnEmployee() throws Exception {
        mockMvc.perform(get("/employee/1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.payload.id", is("1")))
            .andExpect(jsonPath("$.payload.employeeId", is("2017-001")))
            .andExpect(jsonPath("$.payload.firstName", is("Nikki Nicholas")))
            .andExpect(jsonPath("$.payload.middleName", is("Domingo")))
            .andExpect(jsonPath("$.payload.lastName", is("Romero")))
            .andExpect(jsonPath("$.payload.gender", is("Male")))
            .andExpect(jsonPath("$.payload.birthDate", is(LocalDate.of(1991, Month.AUGUST, 5).format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.dateCreated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.dateLastUpdated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))));
    }

    @Test
    public void findAll_whenResultIsNotEmpty_thenReturnList() throws Exception {
        mockMvc.perform(get("/employee"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.payload.[*]", hasSize(3)))
            .andExpect(jsonPath("$.payload.[0].id", is("1")))
            .andExpect(jsonPath("$.payload.[0].employeeId", is("2017-001")))
            .andExpect(jsonPath("$.payload.[0].firstName", is("Nikki Nicholas")))
            .andExpect(jsonPath("$.payload.[0].middleName", is("Domingo")))
            .andExpect(jsonPath("$.payload.[0].lastName", is("Romero")))
            .andExpect(jsonPath("$.payload.[0].gender", is("Male")))
            .andExpect(jsonPath("$.payload.[0].birthDate", is(LocalDate.of(1991, Month.AUGUST, 5).format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[0].dateCreated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[0].dateLastUpdated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[1].id", is("2")))
            .andExpect(jsonPath("$.payload.[1].employeeId", is("2017-002")))
            .andExpect(jsonPath("$.payload.[1].firstName", is("Leslie Anne")))
            .andExpect(jsonPath("$.payload.[1].middleName", is("Suarez")))
            .andExpect(jsonPath("$.payload.[1].lastName", is("Sayin")))
            .andExpect(jsonPath("$.payload.[1].gender", is("Female")))
            .andExpect(jsonPath("$.payload.[1].birthDate", is(LocalDate.of(1992, Month.AUGUST, 21).format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[1].dateCreated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[1].dateLastUpdated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[2].id", is("3")))
            .andExpect(jsonPath("$.payload.[2].employeeId", is("2017-003")))
            .andExpect(jsonPath("$.payload.[2].firstName", is("Maven Claire")))
            .andExpect(jsonPath("$.payload.[2].middleName", is("Sayin")))
            .andExpect(jsonPath("$.payload.[2].lastName", is("Romero")))
            .andExpect(jsonPath("$.payload.[2].gender", is("Female")))
            .andExpect(jsonPath("$.payload.[2].birthDate", is(LocalDate.of(2016, Month.NOVEMBER, 24).format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.[2].dateCreated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.[2].dateLastUpdated", is(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5).format(DateTimeFormatter.ISO_DATE_TIME))));
    }

    @Test
    public void create_whenValidEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeId("2017-01");
        employee.setFirstName("Nikki Nicholas");
        employee.setMiddleName("Domingo");
        employee.setLastName("Romero");
        employee.setGender("Male");
        employee.setBirthDate(LocalDate.of(1991, Month.AUGUST, 5));
        employee.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));

        mockMvc.perform(put("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee created")));

        mockMvc.perform(get("/employee/" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.payload.id", is(employee.getId())))
            .andExpect(jsonPath("$.payload.employeeId", is(employee.getEmployeeId())))
            .andExpect(jsonPath("$.payload.firstName", is(employee.getFirstName())))
            .andExpect(jsonPath("$.payload.middleName", is(employee.getMiddleName())))
            .andExpect(jsonPath("$.payload.lastName", is(employee.getLastName())))
            .andExpect(jsonPath("$.payload.gender", is(employee.getGender())))
            .andExpect(jsonPath("$.payload.birthDate", is(employee.getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.dateCreated", not(employee.getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.dateLastUpdated", not(employee.getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))));
    }

    @Test
    public void update_whenValidEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeId("2018-01");
        employee.setFirstName("Nikki Nicholasss");
        employee.setMiddleName("Domingooo");
        employee.setLastName("Romerooo");
        employee.setGender("Maleeee");
        employee.setBirthDate(LocalDate.of(1990, Month.AUGUST, 5));
        employee.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));

        mockMvc.perform(patch("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee updated")));

        mockMvc.perform(get("/employee/" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.payload.id", is(employee.getId())))
            .andExpect(jsonPath("$.payload.employeeId", is(employee.getEmployeeId())))
            .andExpect(jsonPath("$.payload.firstName", is(employee.getFirstName())))
            .andExpect(jsonPath("$.payload.middleName", is(employee.getMiddleName())))
            .andExpect(jsonPath("$.payload.lastName", is(employee.getLastName())))
            .andExpect(jsonPath("$.payload.gender", is(employee.getGender())))
            .andExpect(jsonPath("$.payload.birthDate", is(employee.getBirthDate().format(DateTimeFormatter.ISO_DATE))))
            .andExpect(jsonPath("$.payload.dateCreated", is(employee.getDateCreated().format(DateTimeFormatter.ISO_DATE_TIME))))
            .andExpect(jsonPath("$.payload.dateLastUpdated", not(employee.getDateLastUpdated().format(DateTimeFormatter.ISO_DATE_TIME))));
    }

    @Test
    public void delete_whenValidEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setId("1");
        employee.setEmployeeId("2018-01");
        employee.setFirstName("Nikki Nicholasss");
        employee.setMiddleName("Domingooo");
        employee.setLastName("Romerooo");
        employee.setGender("Maleeee");
        employee.setBirthDate(LocalDate.of(1990, Month.AUGUST, 5));
        employee.setDateCreated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));
        employee.setDateLastUpdated(LocalDateTime.of(2017, Month.JANUARY, 2, 3, 4, 5));

        mockMvc.perform(delete("/employee")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(mapper.writeValueAsString(employee)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.SUCCESS.toString())))
            .andExpect(jsonPath("$.message", is("Employee deleted")));

        mockMvc.perform(get("/employee/" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is(Status.FAILED.toString())))
            .andExpect(jsonPath("$.errors.[0]", is("EMPLOYEE_DOES_NOT_EXIST")))
            .andExpect(jsonPath("$.message", is("Employee does not exist")));
    }
}
