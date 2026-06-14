package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.model.*;
import com.sudheer.placement_tracker.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dsa")
@CrossOrigin(origins = "*")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    // GET all DSA sections
    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getSections() {
        return ResponseEntity.ok(
                problemService.getDSASections());
    }

    @GetMapping("/sections/{sectionId}/problems")
    public ResponseEntity<List<Problem>> getProblems(
            @PathVariable Long sectionId,
            @RequestParam(defaultValue = "1") Long userId) {
        return ResponseEntity.ok(
                problemService.getProblemsBySection(sectionId, userId));
    }

    // PUT update problem status
    @PutMapping("/progress/update")
    public ResponseEntity<?> updateProgress(
            @RequestParam Long userId,
            @RequestParam Long problemId,
            @RequestParam String status) {
        try {
            UserProgress progress =
                    problemService.updateStatus(
                            userId, problemId, status);
            return ResponseEntity.ok(progress);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(e.getMessage());
        }
    }

    // GET section progress %
    @GetMapping("/progress/{userId}/section/{sectionId}")
    public ResponseEntity<?> getSectionProgress(
            @PathVariable Long userId,
            @PathVariable Long sectionId) {
        int progress = problemService
                .getSectionProgress(userId, sectionId);
        return ResponseEntity.ok(progress + "%");
    }
}