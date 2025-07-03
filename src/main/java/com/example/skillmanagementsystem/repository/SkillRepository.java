package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
    Optional<Skill> findByNameIgnoreCase(String name);
    List<Skill> findByNameInIgnoreCase(List<String> names);

    @Query("SELECT new map(s.name as skill, COUNT(e.id) as count) " +
            "FROM Employee e JOIN e.skills s GROUP BY s.name")
    List<Map<String, Object>> getSkillCounts();
}
