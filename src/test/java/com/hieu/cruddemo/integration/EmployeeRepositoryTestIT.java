package com.hieu.cruddemo.integration;

import com.hieu.cruddemo.entity.Employee;
import com.hieu.cruddemo.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//This prevents Spring Boot from replacing the configured database with an in-memory one
public class EmployeeRepositoryTestIT {

    @Container
    @ServiceConnection // this tells spring to configure itself
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4-bullseye");
    // the container is spun up and populated with data because
    // spring boot will automatically look for schema.sql and data.sql (placed in resources folder) to run

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void connectionEstablished() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();
    }

    @Test
    void shouldReturnEmployeeById() {
        Employee employee = employeeRepository.findById(1);

        assertThat(employee).isNotNull();
        assertThat(employee.getFirstName()).isEqualTo("Leslie");
    }

    @Test
    void shouldReturn5Employees() {
        List<Employee> employees = employeeRepository.findAll();

        assertThat(employees.size()).isEqualTo(5);
    }
}
