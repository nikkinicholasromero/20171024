package com.demo.service;

import com.demo.domain.Employee;
import com.demo.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private Employee employee;
    private List<Employee> employees;
    private Iterable<Employee> employeeIterable;

    @Before
    public void setup() {
        employeeService = new EmployeeService(employeeRepository);
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

        employeeIterable = () -> employees.iterator();
    }

    @Test
    public void findOne_whenValidId_thenReturnEmployee() {
        when(employeeRepository.findOne(anyString())).thenReturn(employee);

        Employee actual = employeeService.findOne(StringUtils.EMPTY);

        assertThat(actual).isEqualTo(employee);
    }

    @Test
    public void findAll_whenEmployeesIterableNotNull_thenReturnEmployeeList() {
        when(employeeRepository.findAll()).thenReturn(employeeIterable);

        List<Employee> actual = employeeService.findAll();

        assertThat(actual).isEqualTo(employees);
    }

    @Test
    public void findAll_whenEmployeesIterableNull_thenReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(null);

        List<Employee> actual = employeeService.findAll();

        assertThat(actual).isEqualTo(new ArrayList<>());
    }

    @Test
    public void create_whenValidEmployee() {
        employeeService.create(employee);

        verify(employee, times(1)).setDateCreated(any(LocalDateTime.class));
        verify(employee, times(1)).setDateLastUpdated(any(LocalDateTime.class));
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void update_whenValidEmployee() {
        employeeService.update(employee);

        verify(employee, times(0)).setDateCreated(any(LocalDateTime.class));
        verify(employee, times(1)).setDateLastUpdated(any(LocalDateTime.class));
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void delete_whenValidEmployee() {
        employeeService.delete(employee);

        verify(employee, times(0)).setDateCreated(any(LocalDateTime.class));
        verify(employee, times(1)).setDateLastUpdated(any(LocalDateTime.class));
        verify(employeeRepository, times(1)).delete(employee);
    }
}
