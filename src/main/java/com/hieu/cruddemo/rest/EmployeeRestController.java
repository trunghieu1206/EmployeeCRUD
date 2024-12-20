package com.hieu.cruddemo.rest;

import com.hieu.cruddemo.entity.Employee;
import com.hieu.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeRestController {

    private EmployeeService employeeService;

    //inject employee dao
    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //expose /employees and return a list of employees
    @GetMapping("/employees")
    public List<Employee> findEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/employees/{employeeId}")
    public Employee findById(@PathVariable int employeeId) {
        Employee theEmployee =  employeeService.findById(employeeId);

        if(theEmployee == null){
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        return theEmployee;
    }

    @PostMapping("/employees")
    @ResponseStatus(HttpStatus.CREATED) // return 201 as STATUS CODE since it is creating
    public Employee addEmployee(@RequestBody Employee theEmployee) {
        // just in case they pass an id in JSON, set id to 0
        // this is to force a save of new item instead of update
        theEmployee.setId(0);

        if(theEmployee.getFirstName() == null || theEmployee.getFirstName() == ""){
            throw new NullPointerException("firstName cannot be NULL");
        }
        if(theEmployee.getLastName() == null || theEmployee.getLastName() == ""){
            throw new NullPointerException("lastName cannot be NULL");
        }
        if(theEmployee.getEmail() == null || theEmployee.getEmail() == "") {
            throw new NullPointerException("email cannot be NULL");
        }
        Employee dbEmployee = employeeService.save(theEmployee);

        employeeService.save(dbEmployee);

        return dbEmployee;
    }

    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
        Employee dbEmployee = employeeService.save(theEmployee);

        System.out.println("Updated Employee: " + dbEmployee);

        return dbEmployee;
    }

    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId) {
        Employee tempEmployee = employeeService.findById(employeeId);

        //throw exception if null

        if(tempEmployee == null) {
            throw new RuntimeException("Employee id not found - " + employeeId);
        }

        employeeService.deleteById(employeeId);

        return "Deleted employee id - " + employeeId;
    }
}
