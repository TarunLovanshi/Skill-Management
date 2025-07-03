package com.example.skillmanagementsystem.service;

import com.example.skillmanagementsystem.entity.EmployeeProject;

import java.util.List;

public interface EmployeeProjectService {
    EmployeeProject assignProject(Long employeeId, Long projectId);
    List<EmployeeProject> getProjectsByEmployee(Long employeeId);
    List<EmployeeProject> getEmployeesByProject(Long projectId);
    void deleteAssignment(Long id);
    List<EmployeeProject> getAllAssignments();
}
