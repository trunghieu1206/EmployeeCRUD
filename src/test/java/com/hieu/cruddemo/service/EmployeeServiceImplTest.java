package com.hieu.cruddemo.service;

import com.hieu.cruddemo.entity.Employee;
import com.hieu.cruddemo.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void should_return_all_employee(){
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(
                "Leslie",
                "Andrews",
                "leslie@gmail.com"
        ));

        employees.add(new Employee(
                "John",
                "Doe",
                "john@gmail.com"
        ));

        // Mocking calls
        Mockito.when(employeeRepository.findAll())
                .thenReturn(employees);

        // When
        List<Employee> resultEmployees = employeeService.findAll();

        // Then
        Assertions.assertEquals(2, resultEmployees.size());
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findAll();
    }

    @Test
    public void should_return_employee_by_id() {
        // Given
        int id = 1;
        Employee employee = new Employee(
                "John",
                "Doe",
                "john@mail.com"
        );

        // Mock calls
        Mockito.when(employeeRepository.findById(id))
                .thenReturn(employee);

        // When
        Employee resultEmployee = employeeService.findById(id);

        // Then
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals("John", resultEmployee.getFirstName());
        Assertions.assertEquals("Doe", resultEmployee.getLastName());
        Assertions.assertEquals("john@mail.com", resultEmployee.getEmail());
        Mockito.verify(employeeRepository, Mockito.times(1))
                .findById(id);
    }

    
}