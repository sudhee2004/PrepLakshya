package com.sudheer.placement_tracker.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateRoadmap(String targetCompany, String skills) {
        try {
            String prompt = "Create a detailed placement preparation roadmap for a student targeting "
                    + targetCompany + " company. Their current skills are: " + skills
                    + ". Give a week by week study plan covering DSA, aptitude, and technical topics.";

            String requestBody = "{"
                    + "\"contents\": [{\"parts\": [{\"text\": \"" + prompt + "\"}]}]"
                    + "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=" + apiKey))
                    .header("Content-Type", "application/json")
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