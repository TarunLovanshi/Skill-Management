package com.example.skillmanagementsystem.controller;

import com.example.skillmanagementsystem.entity.EmployeeProject;
import com.example.skillmanagementsystem.service.EmployeeProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/employeeProject")
public class EmployeeProjectController {

    @Autowired
    private EmployeeProjectService employeeProjectService;

    @PostMapping("/assignEmployeeProject")
    public ResponseEntity<EmployeeProject> assignEmployeeProject(@RequestBody Map<String, Object> body) {
        try {
            Long empId = Long.parseLong(body.get("employeeId").toString());
            Long projId = Long.parseLong(body.get("projectId").toString());
            EmployeeProject ep = employeeProjectService.assignProject(empId, projId);
            return ResponseEntity.ok(ep);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/getAllEmployeeProjectAssignments")
    public ResponseEntity<List<EmployeeProject>> getAllEmployeeProjectAssignments() {
        return ResponseEntity.ok(employeeProjectService.getAllAssignments());
    }

    @GetMapping("/getEmployeeProjectsByEmployeeId/{employeeId}")
    public ResponseEntity<List<EmployeeProject>> getProjectsByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeProjectService.getProjectsByEmployee(employeeId));
    }

    @GetMapping("/getEmployeesByProjectId/{projectId}")
    public ResponseEntity<List<EmployeeProject>> getEmployeesByProject(@PathVariable Long projectId) {
        return ResponseEntity.ok(employeeProjectService.getEmployeesByProject(projectId));
    }

    @DeleteMapping("/deleteAssignment/{assignmentId}")
    public ResponseEntity<?> deleteAssignment(@PathVariable Long assignmentId) {
        try {
            employeeProjectService.deleteAssignment(assignmentId);
            return ResponseEntity.ok(Map.of("message", "Assignment deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
