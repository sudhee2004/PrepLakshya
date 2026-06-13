package com.sudheer.placement_tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GroqService {

    @Value("${groq.api.key}")
    private String apiKey;

    public String generateRoadmap(String targetCompany, String skills) {
        try {
            String prompt = "Create a detailed placement preparation roadmap for a student targeting "
                    + targetCompany + " company. Their current skills are: " + skills
                    + ". Give a week by week study plan covering DSA, aptitude, and technical topics.";

            // Escape quotes and newlines for JSON safety
            String safePrompt = prompt.replace("\\", "\\\\").replace("\"", "\\\"");

            String requestBody = "{"
                    + "\"model\": \"llama-3.3-70b-versatile\","
                    + "\"messages\": [{\"role\": \"user\", \"content\": \"" + safePrompt + "\"}],"
                    + "\"temperature\": 0.7"
                    + "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.groq.com/openai/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}