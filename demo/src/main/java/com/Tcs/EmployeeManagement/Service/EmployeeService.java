package com.Tcs.EmployeeManagement.Service;

import com.Tcs.EmployeeManagement.Exception.EmployeeNOTFound;
import com.Tcs.EmployeeManagement.Model.EmployeeModel;
import com.Tcs.EmployeeManagement.Repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService
{
    @Autowired
    private EmployeeRepository empRepo;


    public EmployeeModel addEmployee(EmployeeModel empModel)
    {

        return empRepo.save(empModel);
    }

    public List<EmployeeModel> findAllEmployees()
    {
        return empRepo.findAll();
    }


    public EmployeeModel findEmployeeById(Integer id)
    {
        return empRepo.findEmployeeById(id)
                .orElseThrow(() -> new EmployeeNOTFound("Employee By id "+id+"Was NOT Found"));

    }

    public EmployeeModel updateEmployee(EmployeeModel employee)
    {
        return empRepo.save(employee);
    }

    public void deleteEmployee(Integer id)
    {
        EmployeeModel existingEmployee = this.empRepo.findById(id).orElseThrow(() -> new EmployeeNOTFound("Employee By "+id+"Not Found"));

        this.empRepo.delete(existingEmployee);

    }
}
