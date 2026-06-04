package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateRoadmap(
            @RequestParam String targetCompany,
            @RequestParam String skills) {
        try {
            String roadmap = geminiService.generateRoadmap(targetCompany, skills);
            return ResponseEntity.ok(roadmap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}