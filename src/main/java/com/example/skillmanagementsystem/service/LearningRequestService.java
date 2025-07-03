package com.example.skillmanagementsystem.service;

import com.example.skillmanagementsystem.entity.LearningRequest;

import java.util.List;
import java.util.Optional;

public interface LearningRequestService {
    String createLearningRequest(Long requesterId, Long managerId, Long skillId, Long targetEmployeeId, Long projectId, String remark);
    List<LearningRequest> getAllRequests();
    List<LearningRequest> getRequestsByRequester(Long requesterId);
    List<LearningRequest> getRequestsByTargetEmployee(Long targetEmployeeId);
    List<LearningRequest> getRequestsByManager(Long managerId);
    Optional<LearningRequest> getRequestById(Long id);
    LearningRequest save(LearningRequest learningRequest);
}
