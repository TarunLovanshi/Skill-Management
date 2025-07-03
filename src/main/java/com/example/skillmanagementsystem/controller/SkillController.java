package com.example.skillmanagementsystem.controller;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.entity.Skill;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import com.example.skillmanagementsystem.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/skill")
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    @PostMapping("/addSkill")
    public ResponseEntity<?> addSkill(@RequestBody Skill skill) {
        if (skill.getName() == null || skill.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Skill name is required"));
        }

        String skillName = skill.getName().trim();
        if (skillRepository.existsByNameIgnoreCase(skillName)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Skill already exists"));
        }

        skill.setName(skillName);
        Skill savedSkill = skillRepository.save(skill);
        return ResponseEntity.ok(Map.of("message", "Skill added successfully", "skill", savedSkill));
    }


    @PostMapping("/addSkillToEmployee")
    public ResponseEntity<?> addSkillToEmployee(@RequestBody Map<String, Object> request) {
        Long employeeId = Long.parseLong(request.get("employeeId").toString());
        Object skillIdOrName = request.get("skillId") != null ? request.get("skillId") : request.get("skillName");

        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Employee not found"));
        }

        Skill skill = null;
        if (skillIdOrName instanceof Number) {
            Long skillId = Long.parseLong(skillIdOrName.toString());
            skill = skillRepository.findById(skillId).orElse(null);
        } else if (skillIdOrName instanceof String skillNameStr) {
            String skillName = skillNameStr.trim();
            skill = skillRepository.findByNameIgnoreCase(skillName)
                    .orElseGet(() -> skillRepository.save(new Skill(skillName)));
        }

        if (skill == null) {
            return ResponseEntity.status(404).body(Map.of("message", "Skill not found or could not be created"));
        }

        Employee employee = employeeOpt.get();
        if (!employee.getSkills().contains(skill)) {
            employee.getSkills().add(skill);
            employeeRepository.save(employee);
        }

        return ResponseEntity.ok(Map.of("message", "Skill added to employee", "skill", skill));
    }


    @GetMapping("/getAllSkills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        return ResponseEntity.ok(skillRepository.findAll());
    }


    @GetMapping("/getSkillsByEmployee/{employeeId}")
    public ResponseEntity<?> getSkillsByEmployee(@PathVariable Long employeeId) {
        Optional<Employee> empOpt = employeeRepository.findById(employeeId);
        if (empOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Employee not found"));
        }

        List<Skill> skills = new ArrayList<>(empOpt.get().getSkills());
        return ResponseEntity.ok(skills);
    }


    @PutMapping("/updateSkill/{id}")
    public ResponseEntity<?> updateSkillById(@PathVariable Long id, @RequestBody Skill updatedSkill) {
        Optional<Skill> existingSkillOpt = skillRepository.findById(id);
        if (existingSkillOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Skill not found"));
        }

        String updatedName = updatedSkill.getName().trim();
        Skill existingSkill = existingSkillOpt.get();
        existingSkill.setName(updatedName);
        skillRepository.save(existingSkill);

        return ResponseEntity.ok(Map.of("message", "Skill updated successfully", "skill", existingSkill));
    }


    @DeleteMapping("/deleteSkillFromEmployee/{employeeId}/{skillId}")
    public ResponseEntity<?> deleteSkillFromEmployee(@PathVariable Long employeeId, @PathVariable Long skillId) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        Optional<Skill> skillOpt = skillRepository.findById(skillId);

        if (employeeOpt.isEmpty() || skillOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Employee or Skill not found"));
        }

        Employee employee = employeeOpt.get();
        Skill skill = skillOpt.get();

        employee.getSkills().remove(skill);
        employeeRepository.save(employee);

        return ResponseEntity.ok(Map.of("message", "Skill removed from employee"));
    }


    @DeleteMapping("/deleteSkillByName/{skillName}")
    public ResponseEntity<?> deleteSkillByName(@PathVariable String skillName) {
        Optional<Skill> skillOpt = skillRepository.findByNameIgnoreCase(skillName.trim());

        if (skillOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Skill not found"));
        }

        Skill skill = skillOpt.get();
        List<Employee> employees = employeeRepository.findAllBySkillsContaining(skill);

        for (Employee emp : employees) {
            emp.getSkills().remove(skill);
        }

        employeeRepository.saveAll(employees);
        skillRepository.delete(skill);

        return ResponseEntity.ok(Map.of("message", "Skill deleted successfully"));
    }


    @PostMapping("/getEmployeesByMultipleSkills")
    public ResponseEntity<?> getEmployeesByMultipleSkills(@RequestBody List<String> skillNames) {
        if (skillNames == null || skillNames.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "No skill names provided"));
        }

        List<Skill> skills = skillRepository.findByNameInIgnoreCase(skillNames);
        if (skills.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "No matching skills found"));
        }

        List<Employee> allEmployees = employeeRepository.findAll();
        List<Employee> matchedEmployees = new ArrayList<>();

        for (Employee emp : allEmployees) {
            if (emp.getSkills().containsAll(skills)) {
                matchedEmployees.add(emp);
            }
        }

        return ResponseEntity.ok(matchedEmployees);
    }

}