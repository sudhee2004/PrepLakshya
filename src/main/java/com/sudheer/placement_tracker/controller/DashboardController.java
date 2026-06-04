package com.sudheer.placement_tracker.controller;

import com.sudheer.placement_tracker.model.Topic;
import com.sudheer.placement_tracker.model.User;
import com.sudheer.placement_tracker.repository.TopicRepository;
import com.sudheer.placement_tracker.repository.UserRepository;
import com.sudheer.placement_tracker.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getDashboard(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        List<Topic> allTopics = topicRepository.findByUserId(userId);
        List<Topic> completedTopics = topicRepository.findByUserIdAndCompleted(userId, true);
        List<Topic> pendingTopics = topicRepository.findByUserIdAndCompleted(userId, false);
        int progress = topicService.getProgressPercentage(userId);

        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("studentName", user.getName());
        dashboard.put("targetCompany", user.getTargetCompany());
        dashboard.put("totalTopics", allTopics.size());
        dashboard.put("completedTopics", completedTopics.size());
        dashboard.put("pendingTopics", pendingTopics.size());
        dashboard.put("progressPercentage", progress + "%");

        return ResponseEntity.ok(dashboard);
    }
}