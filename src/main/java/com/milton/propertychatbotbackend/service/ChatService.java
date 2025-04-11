package com.milton.propertychatbotbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class ChatService {

    private final WebClient webClient;

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    public ChatService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/completions").build();
    }

    public Mono<String> getChatResponse(String userMessage) {
        // Prompt Engineering
        String prompt = "You are assisting in scheduling property viewings between landlords and tenants. " +
                "Respond to the following message: " + userMessage;

        // Call OpenAI API
        return webClient.post()
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(Map.of(
                        "model", "omni-moderation-latest",
                        "prompt", prompt,
                        "max_tokens", 150
                ))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    // Extract the response text
                    Map<String, Object> choices = (Map<String, Object>) ((Map<String, Object>) response.get("choices")).get(0);
                    return (String) choices.get("text");
                });
    }
}
