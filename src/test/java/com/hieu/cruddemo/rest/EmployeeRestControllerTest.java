package com.hieu.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hieu.cruddemo.entity.Employee;
import com.hieu.cruddemo.service.EmployeeServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = EmployeeRestController.class)
@AutoConfigureMockMvc(addFilters = false)

class EmployeeRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee1;

    private Employee employee2;

    @BeforeEach
    public void init() {
        employee1 = Employee.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@mail.com")
                .build();

        employee2 = Employee.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@mail.com")
                .build();
    }

    @Test
    public void EmployeeController_CreateEmployee_ReturnCreated() throws Exception{
        // mock

        // whenever employeeService.save() is called, it will return the exact Employee object that was passed in
        given(employeeService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        // perform an HTTP request using MockMvc
        ResultActions response = mockMvc.perform(post("/employees")
                .contentType(MediaType.APPLICATION_JSON) // indicate request body is in JSON
                .content(objectMapper.writeValueAsString(employee1))); // converts "employee" object into JSON
        // this will return an employee with id = 0 since it is set in the post mapping in rest controller

        // expect
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee1.getEmail())))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    public void EmployeeController_GetAllEmployee_ReturnEmployee() throws Exception{
        // given
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        // mock
        when(employeeService.findAll()).thenReturn(employeeList);

        // act
        ResultActions response = mockMvc.perform(get("/employees"));

        // expect
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()", CoreMatchers.is(employeeList.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is(employee2.getFirstName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void EmployeeController_FindById_ReturnEmployee() throws Exception{
        // mock
        when(employeeService.findById(1)).thenReturn(employee1);

        // act
        ResultActions response = mockMvc.perform(get("/employees/1"));

        // expect
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee1.getEmail())))
                .andDo(MockMvcResultHandlers.print()); // print information
    }

    @Test
    public void EmployeeController_UpdateEmployee_ReturnEmployee() throws Exception{
        // use any(Employee.class) because what is passed it when we call PUT MAPPING is another instance
        // not the same instance as "employee1" which was initialized in this class
        // al though they do have same values for each attribute
        when(employeeService.save(any(Employee.class))).thenReturn(employee1);

        // act
        ResultActions response = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee1)));

        // debugging
        String jsonResponse = response.andReturn().getResponse().getContentAsString();
        System.out.println("Response JSON: " + jsonResponse);

        // expect
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee1.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee1.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee1.getEmail())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void EmployeeController_DeleteEmployee_ReturnString() throws Exception {
        int employeeId = 1;
        when(employeeService.findById(1)).thenReturn(employee1);
        doNothing().when(employeeService).deleteById(1);

        ResultActions response = mockMvc.perform(delete("/employees/1"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
