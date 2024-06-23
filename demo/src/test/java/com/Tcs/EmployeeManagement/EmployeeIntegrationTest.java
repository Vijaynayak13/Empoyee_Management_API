package com.Tcs.EmployeeManagement;

import com.Tcs.EmployeeManagement.Model.EmployeeModel;
import com.Tcs.EmployeeManagement.Repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeIntegrationTest
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        employeeRepository.deleteAll();
        employeeRepository.saveAll(List.of(
                new EmployeeModel(null, "John", "Doe", "john.doe@example.com", "Developer", "IT", "1234567890"),
                new EmployeeModel(null, "Jane", "Smith", "jane.smith@example.com", "Manager", "HR", "0987654321")
        ));
    }

    @Test
    public void testAddEmployee() throws Exception {
        String newEmployeeJson = "{\"firstName\": \"Alice\", \"lastName\": \"Johnson\", \"email\": \"alice.johnson@example.com\", \"jobtitle\": \"Analyst\", \"department\": \"Finance\", \"phoneno\": \"1122334455\"}";

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newEmployeeJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Alice"))
                .andExpect(jsonPath("$.lastName").value("Johnson"));
    }

    @Test
    public void testGetAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    public void testFindEmployeeById() throws Exception {
        EmployeeModel employee = employeeRepository.save(new EmployeeModel(null, "Robert", "Brown", "robert.brown@example.com", "Designer", "Marketing", "5566778899"));

        mockMvc.perform(get("/api/employee/" + employee.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Robert"))
                .andExpect(jsonPath("$.lastName").value("Brown"));
    }

    @Test
    public void testUpdateEmployee() throws Exception {
        EmployeeModel employee = employeeRepository.save(new EmployeeModel(null, "Emily", "Davis", "emily.davis@example.com", "Engineer", "R&D", "6677889900"));

        String updatedEmployeeJson = "{\"firstName\": \"Emma\", \"lastName\": \"Davis\", \"email\": \"emma.davis@example.com\", \"jobtitle\": \"Engineer\", \"department\": \"R&D\", \"phoneno\": \"6677889900\"}";

        mockMvc.perform(put("/api/employees/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedEmployeeJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Emma"));
    }

    @Test
    public void testDeleteEmployee() throws Exception {
        EmployeeModel employee = employeeRepository.save(new EmployeeModel(null, "George", "White", "george.white@example.com", "Consultant", "Operations", "7788990011"));

        mockMvc.perform(delete("/api/employees/" + employee.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/employee/" + employee.getId()))
                .andExpect(status().isNotFound());
    }


}







































/*Certainly! Let's break down the `EmployeeIntegrationTest` class step by step, explaining each annotation, method, and logic used.

        ### Annotations Explained:

        #### `@SpringBootTest`
        - This annotation is used to specify that the tests should load the complete Spring application context.
        - It launches the Spring Boot application and provides integration testing support.
- It's typically used for integration testing where you need to test the application in a near-production environment.

        #### `@AutoConfigureMockMvc`
        - This annotation is used to automatically configure the `MockMvc` instance.
- `MockMvc` is provided by Spring Test and is used to perform HTTP requests against the application in a controlled environment, without starting a full HTTP server.

        #### `@Autowired`
        - This annotation is used to inject dependencies into the test class. In this case, `MockMvc` and `EmployeeRepository` are injected.

        #### `@BeforeEach`
        - This annotation is used to signal that the annotated method should be executed before each test method in the class.
        - In this test class, it's used to set up the database state before each test by deleting all employees and then saving a predefined set of employees.

        #### `@Test`
        - This annotation identifies a method as a test method.
- JUnit Jupiter (JUnit 5) uses this annotation to recognize methods that should be run as tests.

        ### Methods Explained:

        #### `setUp()`
        - This method is annotated with `@BeforeEach`, so it runs before each test method.
- It cleans up the `EmployeeRepository` by deleting all existing employees and then saves a few initial employees using `saveAll()`.

        #### `tearDown()`
        - Annotated with `@AfterEach`, this method runs after each test method.
        - It cleans up the `EmployeeRepository` by deleting all employees after each test to ensure a clean slate for subsequent tests.

        #### `testAddEmployee()`
        - Tests the functionality of adding a new employee through a POST request to `/api/employees`.
        - Constructs JSON data representing a new employee (`newEmployeeJson`).
        - Uses `MockMvc` to perform a POST request with the JSON data, expecting a `201 Created` status and validating the returned JSON fields.

#### `testGetAllEmployees()`
        - Tests retrieving all employees through a GET request to `/api/employees`.
        - Uses `MockMvc` to perform a GET request and verifies the response status (`200 OK`), checks that the response contains two employees, and validates specific fields of the returned JSON.

        #### `testFindEmployeeById()`
        - Tests finding an employee by ID through a GET request to `/api/employee/{id}`.
        - Saves a new employee to the database (`employeeRepository.save()`).
        - Uses `MockMvc` to perform a GET request for the saved employee's ID and verifies the returned JSON fields.

        #### `testUpdateEmployee()`
        - Tests updating an existing employee through a PUT request to `/api/employees/{id}`.
        - Saves a new employee to the database (`employeeRepository.save()`).
        - Constructs JSON data representing updated employee information (`updatedEmployeeJson`).
        - Uses `MockMvc` to perform a PUT request with the JSON data, expects an `200 OK` status, and validates that the employee information has been updated.

#### `testDeleteEmployee()`
        - Tests deleting an existing employee through a DELETE request to `/api/employees/{id}`.
        - Saves a new employee to the database (`employeeRepository.save()`).
        - Uses `MockMvc` to perform a DELETE request for the saved employee's ID, expects an `200 OK` status.
        - Performs a subsequent GET request to ensure the employee has been deleted (`404 Not Found`).

        ### Overall Logic:
        - **Setup**: Each test method starts with a clean state of the database (`setUp()`), ensuring predictable test outcomes by saving known entities.
        - **Assertions**: Each test method uses `MockMvc` to perform HTTP requests and asserts the expected outcomes based on HTTP status codes (`isOk()`, `isCreated()`, etc.) and JSON content (`jsonPath()` assertions).
        - **Cleanup**: After each test (`tearDown()`), the database is cleaned up to maintain test isolation and repeatability.

### Running the Tests:
        - To run these tests, use IntelliJ IDEA or Maven command line (`mvn test`).
        - These tests interact with the Spring application context and use `MockMvc` to simulate HTTP requests and verify responses without starting a full server.
        - Ensure that your application configuration (`application.properties` or `application.yml`) points to a test MySQL database for these integration tests.

This approach allows for thorough testing of your Spring Boot application's REST API endpoints with database interactions using `MockMvc`, ensuring both functionality and correctness in a controlled environment. */