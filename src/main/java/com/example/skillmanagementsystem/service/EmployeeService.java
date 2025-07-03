package com.example.skillmanagementsystem.service;

import com.example.skillmanagementsystem.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Optional<Employee> getEmployeeById(Long id);
    Employee createEmployee(Employee employee);
    Optional<Employee> updateEmployee(Long id, Employee updatedEmployee);
    boolean deleteEmployee(Long id);
}
