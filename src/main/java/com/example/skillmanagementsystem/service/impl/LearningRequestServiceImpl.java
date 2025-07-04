package com.example.skillmanagementsystem.service.impl;

import com.example.skillmanagementsystem.entity.Employee;
import com.example.skillmanagementsystem.entity.LearningRequest;
import com.example.skillmanagementsystem.entity.Skill;
import com.example.skillmanagementsystem.repository.EmployeeRepository;
import com.example.skillmanagementsystem.repository.LearningRequestRepository;
import com.example.skillmanagementsystem.repository.SkillRepository;
import com.example.skillmanagementsystem.service.LearningRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class LearningRequestServiceImpl implements LearningRequestService {

    @Autowired
    private LearningRequestRepository learningRequestRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private SkillRepository skillRepo;

    @Override
    public String createLearningRequest(Long requesterId, Long managerId, Long skillId, Long targetEmployeeId, Long projectId, String remark) {
        Optional<Employee> requesterOpt = employeeRepo.findById(requesterId);
        Optional<Employee> managerOpt = employeeRepo.findById(managerId);
        Optional<Skill> skillOpt = skillRepo.findById(skillId);

        if (requesterOpt.isEmpty() || managerOpt.isEmpty() || skillOpt.isEmpty()) {
            throw new RuntimeException("Invalid requester, manager, or skill ID");
        }

        LearningRequest request = new LearningRequest();
        request.setRequester(requesterOpt.get());
        request.setRequestedManager(managerOpt.get());
        request.setSkill(skillOpt.get());
        request.setStatus("PENDING");
        request.setRemark(remark != null ? remark : "");

        if (targetEmployeeId != null) {
            employeeRepo.findById(targetEmployeeId).ifPresent(request::setTargetEmployee);
        }

        if (projectId != null) {
            request.setProjectId(projectId);
        }

        learningRequestRepo.save(request);
        return "Request sent to your manager!";
    }

    @Override
    public List<LearningRequest> getAllRequests() {
        return learningRequestRepo.findAll();
    }

    @Override
    public List<LearningRequest> getRequestsByRequester(Long requesterId) {
        return learningRequestRepo.findByRequesterId(requesterId);
    }

    @Override
    public List<LearningRequest> getRequestsByTargetEmployee(Long targetEmployeeId) {
        return learningRequestRepo.findByTargetEmployeeId(targetEmployeeId);
    }

    @Override
    public List<LearningRequest> getRequestsByManager(Long managerId) {
        return learningRequestRepo.findByRequestedManagerId(managerId);
    }

    @Override
    public Optional<LearningRequest> getRequestById(Long id) {
        return learningRequestRepo.findById(id);
    }

    @Override
    public LearningRequest save(LearningRequest learningRequest) {
        return learningRequestRepo.save(learningRequest);
    }
}
