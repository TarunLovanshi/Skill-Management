package com.example.skillmanagementsystem.controller;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/Employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/loginEmployee")
    public ResponseEntity<Map<String, Object>> loginEmployee(@RequestBody Employee loginRequest) {
        Optional<Employee> employeeOpt = employeeRepository.findByGmail(loginRequest.getGmail());
        if (employeeOpt.isPresent()) {
            Employee emp = employeeOpt.get();
            if (emp.getPassword().equals(loginRequest.getPassword())) {
                Map<String, Object> res = new HashMap<>();
                res.put("message", "Login successful");
                res.put("employee", emp);
                return ResponseEntity.ok(res);
            } else {
                return ResponseEntity.status(401).body(Map.<String, Object>of("message", "Invalid password"));
            }
        } else {
            return ResponseEntity.status(401).body(Map.<String, Object>of("message", "User not found"));
        }
    }

    @PostMapping("/logoutEmployee")
    public ResponseEntity<Map<String, Object>> logoutEmployee() {
        return ResponseEntity.ok(Map.<String, Object>of("message", "Logout successful"));
    }

    @PostMapping("/registerEmployee")
    public ResponseEntity<Map<String, Object>> registerEmployee(@RequestBody Map<String, Object> request) {
        try {
            String name = (String) request.get("name");
            String gmail = (String) request.get("gmail");
            String password = (String) request.get("password");
            String department = (String) request.get("department");
            String designation = (String) request.get("designation");
            Long managerId = request.get("managerId") != null ? Long.parseLong(request.get("managerId").toString()) : null;

            if (employeeRepository.findByGmail(gmail).isPresent()) {
                return ResponseEntity.status(409).body(Map.<String, Object>of("message", "Gmail already registered"));
            }

            Employee newEmp = new Employee();
            newEmp.setName(name);
            newEmp.setGmail(gmail);
            newEmp.setPassword(password);
            newEmp.setDepartment(department);
            newEmp.setDesignation(designation);

            employeeRepository.save(newEmp);

            if (managerId != null) {
                Optional<Employee> managerOpt = employeeRepository.findById(managerId);
                if (managerOpt.isEmpty()) {
                    return ResponseEntity.status(400).body(Map.<String, Object>of("message", "Invalid manager ID"));
                }
                newEmp.setManager(managerOpt.get());
            } else {
                newEmp.setManager(newEmp);
            }

            employeeRepository.save(newEmp);

            return ResponseEntity.ok(Map.<String, Object>of(
                    "message", "Employee registered successfully",
                    "employeeId", newEmp.getId()
            ));
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body(Map.<String, Object>of(
                    "message", "Internal Server Error",
                    "error", ex.getMessage()
            ));
        }
    }

    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/get-EmployeeById/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(404).build());
    }

    @GetMapping("/getEmployeesUnderManager/{managerId}")
    public ResponseEntity<List<Employee>> getEmployeesUnderManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(employeeRepository.findByManagerId(managerId));
    }

    @PutMapping("/updateEmployeeById/{id}")
    public ResponseEntity<Map<String, Object>> updateEmployeeById(@PathVariable Long id, @RequestBody Employee updatedEmp) {
        return employeeRepository.findById(id)
                .map(existingEmp -> {
                    existingEmp.setName(updatedEmp.getName());
                    existingEmp.setGmail(updatedEmp.getGmail());
                    existingEmp.setPassword(updatedEmp.getPassword());
                    existingEmp.setDepartment(updatedEmp.getDepartment());
                    existingEmp.setDesignation(updatedEmp.getDesignation());

                    if (updatedEmp.getManager() != null && updatedEmp.getManager().getId() != null) {
                        Optional<Employee> managerOpt = employeeRepository.findById(updatedEmp.getManager().getId());
                        if (managerOpt.isEmpty()) {
                            return ResponseEntity.status(400).body(Map.<String, Object>of("message", "Invalid manager ID"));
                        }
                        existingEmp.setManager(managerOpt.get());
                    } else {
                        existingEmp.setManager(null);
                    }

                    employeeRepository.save(existingEmp);
                    return ResponseEntity.ok(Map.<String, Object>of("message", "Employee updated successfully"));
                })
                .orElse(ResponseEntity.status(404).body(Map.<String, Object>of("message", "Employee not found")));
    }

    @DeleteMapping("/DeleteEmployeeById/{id}")
    public ResponseEntity<Map<String, Object>> deleteEmployeeById(@PathVariable Long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            List<Employee> subordinates = employeeRepository.findByManagerId(id);
            for (Employee emp : subordinates) {
                emp.setManager(null);
            }
            employeeRepository.saveAll(subordinates);
            employeeRepository.deleteById(id);
            return ResponseEntity.ok(Map.<String, Object>of("message", "Employee deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(Map.<String, Object>of("message", "Employee not found"));
        }
    }

    @GetMapping("/getEmployeeScore/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeScore(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(emp -> ResponseEntity.ok(Map.<String, Object>of("score", emp.getScore())))
                .orElse(ResponseEntity.status(404).body(Map.<String, Object>of("message", "Employee not found")));
    }

    @GetMapping("/getEmployeeProjects/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeProjects(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(emp -> ResponseEntity.ok(Map.<String, Object>of("projects", emp.getProjects())))
                .orElse(ResponseEntity.status(404).body(Map.<String, Object>of("message", "Employee not found")));
    }

    @GetMapping("/getEmployeeLearningRequests/{id}")
    public ResponseEntity<Map<String, Object>> getEmployeeLearningRequests(@PathVariable Long id) {
        return employeeRepository.findById(id)
                .map(emp -> ResponseEntity.ok(Map.<String, Object>of("requests", emp.getLearningRequests())))
                .orElse(ResponseEntity.status(404).body(Map.<String, Object>of("message", "Employee not found")));
    }

    @GetMapping("/getEmployeesBySkillName/{skillName}")
    public ResponseEntity<List<Employee>> getEmployeesBySkillName(@PathVariable String skillName) {
        return ResponseEntity.ok(employeeRepository.findBySkills_NameIgnoreCase(skillName));
    }

    @GetMapping("/getAllManagers")
    public ResponseEntity<List<Employee>> getAllManagers() {
        List<Employee> managers = employeeRepository.findAllManagers();
        return ResponseEntity.ok(managers);
    }
}
