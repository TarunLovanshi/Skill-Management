package com.example.skillmanagementsystem.service.impl;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.entity.EmployeeSkill;
import com.example.skillmanagementsystem.entity.Skill;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import com.example.skillmanagementsystem.repository.EmployeeSkillRepository;
import com.example.skillmanagementsystem.repository.SkillRepository;
import com.example.skillmanagementsystem.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeSkillServiceImpl implements EmployeeSkillService {

    @Autowired
    private EmployeeSkillRepository employeeSkillRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Override
    public EmployeeSkill addOrUpdateSkill(Long employeeId, Long skillId, int score) {
        Employee employee = employeeRepo.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Skill skill = skillRepo.findById(skillId)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        List<EmployeeSkill> existingSkills = employeeSkillRepo.findByEmployee_Id(employeeId);
        for (EmployeeSkill es : existingSkills) {
            if (es.getSkill().getId().equals(skillId)) {
                return employeeSkillRepo.save(es);
            }
        }

        EmployeeSkill employeeSkill = new EmployeeSkill(employee, skill);
        return employeeSkillRepo.save(employeeSkill);
    }

    @Override
    public List<EmployeeSkill> getSkillsByEmployee(Long employeeId) {
        return employeeSkillRepo.findByEmployee_Id(employeeId);
    }

    @Override
    public void deleteEmployeeSkill(Long id) {
        employeeSkillRepo.deleteById(id);
    }

    @Override
    public List<EmployeeSkill> getAll() {
        return employeeSkillRepo.findAll();
    }
}
