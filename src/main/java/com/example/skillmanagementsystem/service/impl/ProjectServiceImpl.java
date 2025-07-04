package com.example.skillmanagementsystem.service.impl;

import com.example.skillmanagementsystem.entity.Project;
import com.example.skillmanagementsystem.repository.ProjectRepository;
import com.example.skillmanagementsystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Override
    public Project create(Project project) {
        project.setCreatedDate(new Date());
        project.setUpdatedDate(new Date());
        return projectRepo.save(project);
    }

    @Override
    public List<Project> getAll() {
        return projectRepo.findAll();
    }

    @Override
    public Project getById(Long id) {
        return projectRepo.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        projectRepo.deleteById(id);
    }

    @Override
    public Project update(Long id, Project updatedProject) {
        Project project = projectRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with ID: " + id));

        project.setName(updatedProject.getName());
        project.setDescription(updatedProject.getDescription());
        project.setStartDate(updatedProject.getStartDate());
        project.setEndDate(updatedProject.getEndDate());
        project.setTechnology(updatedProject.getTechnology());
        project.setUpdatedDate(new Date());

        return projectRepo.save(project);
    }
}
