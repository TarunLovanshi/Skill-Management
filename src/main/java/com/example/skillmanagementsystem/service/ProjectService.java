package com.example.skillmanagementsystem.service;

import com.example.skillmanagementsystem.entity.Project;
import java.util.List;

public interface ProjectService {
    Project create(Project project);
    List<Project> getAll();
    Project getById(Long id);
    void delete(Long id);
    Project update(Long id, Project updatedProject);
}
