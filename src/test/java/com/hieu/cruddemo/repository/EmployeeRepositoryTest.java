package com.hieu.cruddemo.repository;

import com.hieu.cruddemo.entity.Employee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class EmployeeRepositoryTest {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeRepositoryTest(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Test
    public void EmployeeRepository_SaveAll_ReturnSavedEmployee() {
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

    @Test
    public void EmployeeRepository_FindById_ReturnEmployee() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@mail.com")
                .build();

        employeeRepository.save(employee);

        Employee resultEmployee = employeeRepository.findById(employee.getId());

        Assertions.assertThat(resultEmployee).isNotNull();

    }

    @Test
    public void EmployeeRepository_EmployeeDelete_ReturnEmployeeIsEmpty() {
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@mail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.deleteById(employee.getId());

        Employee findEmployee = employeeRepository.findById(employee.getId());

        Assertions.assertThat(findEmployee).isNull();
    }
}