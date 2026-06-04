package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.model.Topic;
import com.sudheer.placement_tracker.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @PostMapping("/add")
    public ResponseEntity<?> addTopic(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam String category) {
        try {
            Topic topic = topicService.addTopic(userId, name, category);
            return ResponseEntity.ok(topic);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/complete/{topicId}")
    public ResponseEntity<?> markCompleted(@PathVariable Long topicId) {
        try {
            Topic topic = topicService.markCompleted(topicId);
            return ResponseEntity.ok(topic);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Topic>> getUserTopics(@PathVariable Long userId) {
        return ResponseEntity.ok(topicService.getUserTopics(userId));
    }

    @GetMapping("/progress/{userId}")
    public ResponseEntity<?> getProgress(@PathVariable Long userId) {
        int progress = topicService.getProgressPercentage(userId);
        return ResponseEntity.ok("Progress: " + progress + "%");
    }
}