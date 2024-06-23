package com.Tcs.EmployeeManagement.Repository;

import com.Tcs.EmployeeManagement.Model.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, Integer>
{

    Optional<EmployeeModel> findEmployeeById(Integer id);
}
