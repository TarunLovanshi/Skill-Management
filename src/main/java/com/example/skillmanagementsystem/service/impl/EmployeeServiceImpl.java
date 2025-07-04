package com.example.skillmanagementsystem.service.impl;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import com.example.skillmanagementsystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id).map(existingEmployee -> {
            existingEmployee.setName(updatedEmployee.getName());
            existingEmployee.setGmail(updatedEmployee.getGmail());
            existingEmployee.setPassword(updatedEmployee.getPassword());
            existingEmployee.setDepartment(updatedEmployee.getDepartment());
            existingEmployee.setDesignation(updatedEmployee.getDesignation());
            existingEmployee.setManager(updatedEmployee.getManager());

            return employeeRepository.save(existingEmployee);
        });
    }

    @Override
    public boolean deleteEmployee(Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            List<Employee> subordinates = employeeRepository.findByManagerId(id);
            for (Employee subordinate : subordinates) {
                subordinate.setManager(null);
            }
            employeeRepository.saveAll(subordinates);

            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
