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

    // GET all DSA sections only
    @GetMapping("/sections")
    public ResponseEntity<List<Section>> getSections() {
        return ResponseEntity.ok(
                problemService.getDSASections());
    }

    // GET ALL sections regardless of category (DSA + Aptitude + others)
    @GetMapping("/sections/all")
    public ResponseEntity<List<Section>> getAllSections() {
        return ResponseEntity.ok(
                problemService.getAllSections());
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

    @GetMapping("/progress/overall/{userId}")
    public ResponseEntity<?> getOverallProgress(@PathVariable Long userId) {
        return ResponseEntity.ok(problemService.getOverallProgress(userId));
    }
    // POST add new section
    @PostMapping("/sections/add")
    public ResponseEntity<?> addSection(
            @RequestParam String name,
            @RequestParam String category) {
        try {
            Section section = problemService.addSection(name, category);
            return ResponseEntity.ok(section);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE section
    @DeleteMapping("/sections/{sectionId}")
    public ResponseEntity<?> deleteSection(@PathVariable Long sectionId) {
        try {
            problemService.deleteSection(sectionId);
            return ResponseEntity.ok("Section deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // POST add new problem
    @PostMapping("/problems/add")
    public ResponseEntity<?> addProblem(
            @RequestParam String title,
            @RequestParam String difficulty,
            @RequestParam String link,
            @RequestParam Long sectionId) {
        try {
            Problem problem = problemService.addProblem(title, difficulty, link, sectionId);
            return ResponseEntity.ok(problem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE problem
    @DeleteMapping("/problems/{problemId}")
    public ResponseEntity<?> deleteProblem(@PathVariable Long problemId) {
        try {
            problemService.deleteProblem(problemId);
            return ResponseEntity.ok("Problem deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}