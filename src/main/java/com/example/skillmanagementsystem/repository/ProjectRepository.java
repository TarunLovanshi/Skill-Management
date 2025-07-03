package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByNameContainingIgnoreCase(String name);
    List<Project> findByTechnologyContainingIgnoreCase(String technology);
    List<Project> findByStartDateAfter(Date date);
}
