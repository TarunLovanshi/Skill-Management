package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.EmployeeProject;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeeProjectRepository extends JpaRepository<EmployeeProject, Long> {
    List<EmployeeProject> findByEmployeeId(Long employeeId);
    List<EmployeeProject> findByProjectId(Long projectId);
}
