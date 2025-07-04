package com.example.skillmanagementsystem.service;

import com.example.skillmanagementsystem.entity.EmployeeSkill;

import java.util.List;

public interface EmployeeSkillService {

    EmployeeSkill addOrUpdateSkill(Long employeeId, Long skillId, int score);

    List<EmployeeSkill> getSkillsByEmployee(Long employeeId);

    void deleteEmployeeSkill(Long id);

    List<EmployeeSkill> getAll();
}
