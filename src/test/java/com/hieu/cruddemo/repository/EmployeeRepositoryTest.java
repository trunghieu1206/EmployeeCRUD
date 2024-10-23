package com.hieu.cruddemo.repository;

import com.hieu.cruddemo.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    public void EmployeeRepository_SaveAll_ReturnSavedPokemon() {
        // Arrange
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@mail.com")
                .build();

        // Act
        Employee savedEmployee = employeeRepository.save(employee);

        // Assert
        Assertions.assertThat(savedEmployee).isNotNull();
        Assertions.assertThat(savedEmployee.getId()).isEqualTo(6);

    }

    @Test
    public void EmployeeRepository_GetAll_ReturnMoreThanOneEmployee() {
        // Arrange
        Employee employee1 = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@mail.com")
                .build();

        // Arrange
        Employee employee2 = Employee.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@mail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);

        // Act
        List<Employee> employees = employeeRepository.findAll();

        // Assert
        Assertions.assertThat(employees).isNotNull();
        Assertions.assertThat(employees.size()).isEqualTo(7);

    }
}