package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByGmail(String gmail);
    boolean existsByGmail(String gmail);

    List<Employee> findByManagerId(Long managerId);

    List<Employee> findByNameContainingIgnoreCase(String name);
    List<Employee> findByDepartment(String department);
    List<Employee> findByDesignation(String designation);
    List<Employee> findByScoreGreaterThan(int score);

    List<Employee> findBySkills_NameIgnoreCase(String skillName);
    List<Employee> findAllBySkillsContaining(Skill skill);
    List<Employee> findDistinctBySkillsIn(List<Skill> skills);

    @Query("SELECT DISTINCT e.manager FROM Employee e WHERE e.manager IS NOT NULL")
    List<Employee> findAllManagers();
}
