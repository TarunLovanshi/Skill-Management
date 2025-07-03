package com.example.skillmanagementsystem.service.impl;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.entity.EmployeeProject;
import com.example.skillmanagementsystem.repository.EmployeeProjectRepository;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import com.example.skillmanagementsystem.service.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private EmployeeProjectRepository employeeProjectRepo;

    @Override
    public EmployeeProject assignProject(Long employeeId, Long projectId) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        EmployeeProject ep = new EmployeeProject(employee, projectId);
        return employeeProjectRepo.save(ep);
    }

    @Override
    public List<EmployeeProject> getProjectsByEmployee(Long employeeId) {
        return employeeProjectRepo.findByEmployeeId(employeeId);
    }

    @Override
    public List<EmployeeProject> getEmployeesByProject(Long projectId) {
        return employeeProjectRepo.findByProjectId(projectId);
    }

    @Override
    public void deleteAssignment(Long id) {
        employeeProjectRepo.deleteById(id);
    }

    @Override
    public List<EmployeeProject> getAllAssignments() {
        return employeeProjectRepo.findAll();
    }
}
