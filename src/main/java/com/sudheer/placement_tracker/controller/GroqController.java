package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.service.GroqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "*")
public class GroqController {

    @Autowired
    private GroqService groqService;

    @PostMapping("/roadmap")
    public ResponseEntity<?> generateRoadmap(@RequestBody Map<String, String> body) {
        try {
            String topic = body.get("topic");
            String skills = body.get("skills") != null ? body.get("skills") : "" ;
            String roadmap = groqService.generateRoadmap(topic,skills);
            return ResponseEntity.ok(Map.of("roadmap", roadmap));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}