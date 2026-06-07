package com.sudheer.placement_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "*")
public class ProfileController {

    @GetMapping("/leetcode/{username}")
    public ResponseEntity<?> getLeetCodeProfile(@PathVariable String username) {
        try {
            String query = "{\"query\":\"{ matchedUser(username: \\\"" + username + "\\\") { username submitStats: submitStatsGlobal { acSubmissionNum { difficulty count submissions } } profile { ranking reputation } } }\"}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://leetcode.com/graphql"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(query))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return ResponseEntity.ok(response.body());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/hackerrank/{username}")
    public ResponseEntity<?> getHackerRankProfile(@PathVariable String username) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.hackerrank.com/rest/hackers/" + username + "/badges"))
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return ResponseEntity.ok(response.body());
            } else {
                return ResponseEntity.ok("{\"profile_url\": \"https://www.hackerrank.com/profile/" + username + "\", \"username\": \"" + username + "\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.ok("{\"profile_url\": \"https://www.hackerrank.com/profile/" + username + "\", \"username\": \"" + username + "\"}");
        }
    }
}