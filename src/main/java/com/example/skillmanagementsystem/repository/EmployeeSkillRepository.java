package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Long> {
    List<EmployeeSkill> findByEmployee_Id(Long employeeId);
    List<EmployeeSkill> findBySkill_Id(Long skillId);
}
