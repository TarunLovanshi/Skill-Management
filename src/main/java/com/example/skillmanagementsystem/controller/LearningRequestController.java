package com.example.skillmanagementsystem.controller;

import com.example.skillmanagementsystem.entity.LearningRequest;
import com.example.skillmanagementsystem.service.LearningRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/learningRequest")
public class LearningRequestController {

    @Autowired
    private LearningRequestService learningRequestService;

    public static class LearningRequestDTO {
        public Long requestedId;
        public Long requestedManagerId;
        public Long targetEmployeeId;
        public Long skillId;
        public Long projectId;
        public String remark;
    }

    @PostMapping("/addLearningRequest")
    public ResponseEntity<?> addLearningRequest(@RequestBody LearningRequestDTO dto) {
        try {
            String message = learningRequestService.createLearningRequest(
                    dto.requestedId,
                    dto.requestedManagerId,
                    dto.skillId,
                    dto.targetEmployeeId,
                    dto.projectId,
                    dto.remark
            );
            return ResponseEntity.ok(Map.of("message", message));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        Optional<LearningRequest> opt = learningRequestService.getRequestById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("message", "Learning request not found"));
        }

        String status = body.get("status");
        Set<String> validStatuses = Set.of("APPROVED", "REJECTED");

        if (status == null || !validStatuses.contains(status.toUpperCase())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Status must be APPROVED or REJECTED"));
        }

        LearningRequest request = opt.get();
        request.setStatus(status.toUpperCase());

        if ("APPROVED".equalsIgnoreCase(status)) {
            String requesterName = request.getRequester().getName();
            String skillName = request.getSkill().getName();
            request.setTargetMessage("Your manager approved a request from " + requesterName + " to learn " + skillName + ".");
        } else {
            request.setTargetMessage(null);
        }

        learningRequestService.save(request);

        return ResponseEntity.ok(Map.of(
                "message", "Status updated successfully",
                "requestId", request.getId(),
                "newStatus", request.getStatus()
        ));
    }

    @GetMapping("/All")
    public ResponseEntity<List<LearningRequest>> getAllRequests() {
        return ResponseEntity.ok(learningRequestService.getAllRequests());
    }

    @GetMapping("/byRequester/{requesterId}")
    public ResponseEntity<List<LearningRequest>> getRequestsByRequester(@PathVariable Long requesterId) {
        return ResponseEntity.ok(learningRequestService.getRequestsByRequester(requesterId));
    }

    @GetMapping("/byTarget/{targetEmployeeId}")
    public ResponseEntity<List<LearningRequest>> getRequestsByTargetEmployee(@PathVariable Long targetEmployeeId) {
        return ResponseEntity.ok(learningRequestService.getRequestsByTargetEmployee(targetEmployeeId));
    }

    @GetMapping("/byManager/{managerId}")
    public ResponseEntity<List<LearningRequest>> getRequestsByManager(@PathVariable Long managerId) {
        return ResponseEntity.ok(learningRequestService.getRequestsByManager(managerId));
    }

    @GetMapping("/byTarget/{targetEmployeeId}/approved")
    public ResponseEntity<List<LearningRequest>> getApprovedRequestsForTarget(@PathVariable Long targetEmployeeId) {
        List<LearningRequest> all = learningRequestService.getRequestsByTargetEmployee(targetEmployeeId);
        List<LearningRequest> approved = new ArrayList<>();
        for (LearningRequest req : all) {
            if ("APPROVED".equalsIgnoreCase(req.getStatus()) && req.getTargetMessage() != null) {
                approved.add(req);
            }
        }
        return ResponseEntity.ok(approved);
    }
}
