package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

    @Autowired
    private GroqService groqService;

    @GetMapping("/generate")
    public ResponseEntity<?> generateRoadmap(
            @RequestParam String targetCompany,
            @RequestParam String skills) {
        try {
            String roadmap = groqService.generateRoadmap(targetCompany, skills);
            return ResponseEntity.ok(roadmap);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}