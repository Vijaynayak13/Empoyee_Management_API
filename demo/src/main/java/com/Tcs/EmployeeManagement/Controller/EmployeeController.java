package com.Tcs.EmployeeManagement.Controller;

import com.Tcs.EmployeeManagement.Model.EmployeeModel;
import com.Tcs.EmployeeManagement.Service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")

public class EmployeeController {

    private final EmployeeService empService;
    public EmployeeController(EmployeeService empService) {
        this.empService = empService;
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeModel> addEmployee(@RequestBody EmployeeModel empModel)
    {
        EmployeeModel newemp =empService.addEmployee(empModel);
        return new ResponseEntity<>(newemp, HttpStatus.CREATED);
    }


    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeModel>> getAllEmployees()
    {
        List<EmployeeModel> employeeModels = empService.findAllEmployees();
        return new ResponseEntity<>(employeeModels, HttpStatus.OK);

    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeModel> findEmployeeById(@PathVariable("id")Integer id)
    {
        EmployeeModel findemployee= empService.findEmployeeById(id);
        return new ResponseEntity<>(findemployee, HttpStatus.OK);
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<EmployeeModel> updateEmployee(@PathVariable("id") Integer id,@RequestBody
                                                        EmployeeModel employee)
    {

        employee.setId(id);
        EmployeeModel updateEmployee = empService.updateEmployee(employee);
        return new ResponseEntity<>(updateEmployee, HttpStatus.OK);

    }


    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?>  deleteEmployee(@PathVariable("id") Integer id)
    {
        empService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
