package com.hieu.cruddemo.integration;

import com.hieu.cruddemo.entity.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Testcontainers // tells spring to scan this class for container annotation
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EmployeeRestControllerTestIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.4-bullseye");

    @Autowired
    TestRestTemplate restTemplate;
    // Spring-provided utility that simplifies making HTTP requests during integration tests

    @Test
    public void shouldFindAllEmployees() {
        // call /employees
        // returns an array of employees
        Employee[] employees = restTemplate.getForObject("/employees", Employee[].class);

        assertThat(employees.length).isEqualTo(5);
    }

    @Test
    public void shouldFindEmployeeByID() {
        ResponseEntity<Employee> response = restTemplate.exchange("/employees/1", HttpMethod.GET, null, Employee.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();

    }
}
