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
            String skills = body.get("skills") != null ? body.get("skills") : "";
            String roadmap = groqService.generateRoadmap(topic, skills);
            return ResponseEntity.ok(Map.of("roadmap", roadmap));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/interview/start")
    public ResponseEntity<?> startInterview(@RequestBody Map<String, String> body) {
        try {
            String interviewType = body.get("interviewType");
            String question = groqService.generateInterviewQuestion(interviewType, "1");
            return ResponseEntity.ok(Map.of("question", question, "questionNumber", 1));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/interview/answer")
    public ResponseEntity<?> submitAnswer(@RequestBody Map<String, String> body) {
        try {
            String question = body.get("question");
            String answer = body.get("answer");
            String interviewType = body.get("interviewType");
            String nextQuestionNumber = body.get("nextQuestionNumber");

            String evaluation = groqService.evaluateAnswer(question, answer, interviewType);
            String nextQuestion = groqService.generateInterviewQuestion(interviewType, nextQuestionNumber);

            return ResponseEntity.ok(Map.of(
                    "evaluation", evaluation,
                    "nextQuestion", nextQuestion,
                    "questionNumber", Integer.parseInt(nextQuestionNumber)
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
}