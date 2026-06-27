package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.model.InterviewExperience;
import com.sudheer.placement_tracker.repository.InterviewExperienceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/experiences")
@CrossOrigin(origins = "*")
public class InterviewExperienceController {

    @Autowired
    private InterviewExperienceRepository repo;

    // Get all approved experiences (public wall)
    @GetMapping("/approved")
    public List<InterviewExperience> getApproved() {
        return repo.findByApprovedTrue();
    }

    // Get pending experiences (admin only)
    @GetMapping("/pending")
    public List<InterviewExperience> getPending() {
        return repo.findByApprovedFalse();
    }

    // Submit new experience (logged in user)
    @PostMapping("/submit")
    public ResponseEntity<?> submit(@RequestBody InterviewExperience exp) {
        exp.setApproved(false);
        repo.save(exp);
        return ResponseEntity.ok(Map.of("message", "Experience submitted! Waiting for admin approval."));
    }

    // Admin approve
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        return repo.findById(id).map(exp -> {
            exp.setApproved(true);
            repo.save(exp);
            return ResponseEntity.ok(Map.of("message", "Approved!"));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Admin reject/delete
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Rejected and deleted!"));
    }

    // Filter by company
    @GetMapping("/company/{name}")
    public List<InterviewExperience> byCompany(@PathVariable String name) {
        return repo.findByCompanyIgnoreCaseAndApprovedTrue(name);
    }
}