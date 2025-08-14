package com.alura.literalura.service;

import com.alura.literalura.dto.GutendexBookDto;
import com.alura.literalura.dto.GutendexResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Optional;

@Service
public class GutendexClientService {

    private static final String BASE = "https://gutendex.com/books";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public GutendexClientService(ObjectMapper objectMapper) {
        this.httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = objectMapper;
    }


    public Optional<GutendexBookDto> searchFirstBookByTitle(String title) {
        try {
            String encoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
            URI uri = URI.create(BASE + "?search=" + encoded);

            HttpRequest req = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .timeout(Duration.ofSeconds(20))
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                String body = resp.body();
                GutendexResponse response = objectMapper.readValue(body, GutendexResponse.class);
                if (response.getResults() != null && !response.getResults().isEmpty()) {
                    return Optional.of(response.getResults().get(0));
                } else {
                    return Optional.empty();
                }
            } else {
                System.err.println("Gutendex returned non-2xx: " + resp.statusCode());
                return Optional.empty();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}