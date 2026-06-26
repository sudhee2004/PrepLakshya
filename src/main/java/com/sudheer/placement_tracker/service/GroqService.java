package com.sudheer.placement_tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.List;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String GROQ_URL = "https://api.groq.com/openai/v1/chat/completions";

    public String generateRoadmap(String targetCompany, String skills) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String prompt = "Generate a detailed study roadmap for placement at "
                + targetCompany + ". My current skills are: " + skills
                + ". Include topics, resources and timeline.";

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, request, Map.class);
        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return message.get("content").toString();
    }

    public String generateInterviewQuestion(String interviewType, String questionNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String prompt = "You are a strict technical interviewer conducting a " + interviewType + " interview. "
                + "Ask question number " + questionNumber + " only. "
                + "Just ask the question directly, no intro text. "
                + "For DSA: ask about arrays, linked lists, trees, sorting, searching, dynamic programming. "
                + "For Core CS: ask about OS, DBMS, Computer Networks, OOPs concepts. "
                + "Keep it short and clear.";

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, request, Map.class);
        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return message.get("content").toString();
    }

    public String evaluateAnswer(String question, String userAnswer, String interviewType) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        String prompt = "You are a technical interviewer evaluating a candidate's answer. "
                + "Interview type: " + interviewType + ". "
                + "Question asked: " + question + ". "
                + "Candidate's answer: " + userAnswer + ". "
                + "Give response in this exact format:\n"
                + "SCORE: X/10\n"
                + "FEEDBACK: (2-3 lines of specific feedback)\n"
                + "CORRECT ANSWER: (brief ideal answer)\n"
                + "Keep it concise and encouraging but honest.";

        Map<String, Object> body = Map.of(
                "model", "llama-3.3-70b-versatile",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(GROQ_URL, request, Map.class);
        List<Map> choices = (List<Map>) response.getBody().get("choices");
        Map message = (Map) choices.get(0).get("message");
        return message.get("content").toString();
    }
}