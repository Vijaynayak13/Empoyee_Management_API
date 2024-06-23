package com.Tcs.Unit_Testing;

import com.Tcs.EmployeeManagement.Exception.EmployeeNOTFound;
import com.Tcs.EmployeeManagement.Model.EmployeeModel;
import com.Tcs.EmployeeManagement.Repository.EmployeeRepository;
import com.Tcs.EmployeeManagement.Service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EmployeeServiceJunitTest
{
    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    public EmployeeServiceJunitTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddEmployee() {
        EmployeeModel employee = new EmployeeModel(null, "Test", "User", "test.user@example.com", "Developer", "IT", "1234567890");
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employee);

        EmployeeModel createdEmployee = employeeService.addEmployee(employee);

        assertEquals("Test", createdEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
        System.out.println("AddEmployee() Method is Tested Successfully");
        System.out.println(createdEmployee);
    }

    @Test
    public void testFindAllEmployees() {
        employeeService.findAllEmployees();
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testFindEmployeeById() {
        EmployeeModel employee = new EmployeeModel(1, "Test", "User", "test.user@example.com", "Developer", "IT", "1234567890");
        when(employeeRepository.findEmployeeById(anyInt())).thenReturn(Optional.of(employee));

        EmployeeModel foundEmployee = employeeService.findEmployeeById(1);

        assertEquals("Test", foundEmployee.getFirstName());
        verify(employeeRepository, times(1)).findEmployeeById(1);
    }

    @Test
    public void testFindEmployeeByIdNotFound() {
        when(employeeRepository.findEmployeeById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EmployeeNOTFound.class, () -> employeeService.findEmployeeById(1));
    }

    @Test
    public void testUpdateEmployee() {
        EmployeeModel employee = new EmployeeModel(1, "Test", "User", "test.user@example.com", "Developer", "IT", "1234567890");
        when(employeeRepository.save(any(EmployeeModel.class))).thenReturn(employee);

        EmployeeModel updatedEmployee = employeeService.updateEmployee(employee);

        assertEquals("Test", updatedEmployee.getFirstName());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testDeleteEmployee() {
        EmployeeModel employee = new EmployeeModel(1, "Test", "User", "test.user@example.com", "Developer", "IT", "1234567890");
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));

        employeeService.deleteEmployee(1);

        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    public void testDeleteEmployeeNotFound() {
        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EmployeeNOTFound.class, () -> employeeService.deleteEmployee(1));
    }

}
