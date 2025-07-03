package com.example.skillmanagementsystem.repository;

import com.example.skillmanagementsystem.entity.LearningRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LearningRequestRepository extends JpaRepository<LearningRequest, Long> {
    List<LearningRequest> findByRequesterId(Long requesterId);
    List<LearningRequest> findByTargetEmployeeId(Long targetEmployeeId);
    List<LearningRequest> findByRequestedManagerId(Long requestedManagerId);
}
