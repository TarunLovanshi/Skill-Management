package com.example.skillmanagementsystem.controller;

import com.example.skillmanagementsystem.entity.EmployeeSkill;
import com.example.skillmanagementsystem.service.EmployeeSkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/EmployeeSkill")
public class EmployeeSkillController {

    @Autowired
    private EmployeeSkillService employeeSkillService;

    @PostMapping("/assignEmployeeSkill")
    public ResponseEntity<?> assignEmployeeSkill(@RequestBody Map<String, Object> request) {
        try {
            Long employeeId = Long.parseLong(request.get("employeeId").toString());
            Long skillId = Long.parseLong(request.get("skillId").toString());
            int score = Integer.parseInt(request.get("score").toString());

            EmployeeSkill result = employeeSkillService.addOrUpdateSkill(employeeId, skillId, score);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/getEmployeeSkillsById/{employeeId}")
    public ResponseEntity<List<EmployeeSkill>> getEmployeeSkillsById(@PathVariable Long employeeId) {
        List<EmployeeSkill> skills = employeeSkillService.getSkillsByEmployee(employeeId);
        return ResponseEntity.ok(skills);
    }
}
