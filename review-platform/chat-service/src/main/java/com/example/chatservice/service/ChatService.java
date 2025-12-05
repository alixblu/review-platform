package com.example.chatservice.service;

import com.example.chatservice.dto.ChatRequest;
import com.example.chatservice.dto.ChatResponse;
import com.example.chatservice.httpclient.UserClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.access-key-id}")
    private String awsAccessKeyId;

    @Value("${aws.secret-access-key}")
    private String awsSecretAccessKey;

    @Value("${bedrock.agent.id}")
    private String agentId;

    @Value("${bedrock.agent.alias-id}")
    private String agentAliasId;

    @Value("${bedrock.agent.api-url:https://xgd5sz06fc.execute-api.ap-southeast-2.amazonaws.com/dev/agent}")
    private String bedrockAgentApiUrl;

    public ChatResponse chat(ChatRequest request) {
        try {
            log.info("Received chat request from user: {}", request.getUserId());
            
            // 1. Fetch user preference (can be null)
            String userPreference = fetchUserPreference(request.getUserId());
            log.info("User preference fetched: {}", userPreference != null ? "Yes" : "No");

            // 2. Build input for agent
            String agentInput = buildAgentInput(request.getMessage(), userPreference);
            log.info("Agent input prepared, length: {}", agentInput.length());

            // 3. Call Bedrock Agent
            log.info("Calling Bedrock Agent...");
            String agentResponse = invokeBedrockAgent(agentInput);
            log.info("Agent Response received, length: {}", agentResponse.length());

            // 4. Parse response
            return parseAgentResponse(agentResponse);

        } catch (Exception e) {
            log.error("Error in chat service: {}", e.getMessage(), e);
            log.error("Error type: {}", e.getClass().getName());
            if (e.getCause() != null) {
                log.error("Caused by: {}", e.getCause().getMessage());
            }
            return ChatResponse.builder()
                    .message("Xin lỗi, đã có lỗi xảy ra. Vui lòng thử lại sau. Error: " + e.getMessage())
                    .build();
        }
    }

    private String fetchUserPreference(String userId) {
        if (userId == null || userId.equals("guest")) {
            return null;
        }
        
        try {
            Object response = userClient.getUserPreference(userId);
            String jsonResponse = objectMapper.writeValueAsString(response);
            log.info("User preference fetched for user: {}", userId);
            return jsonResponse;
        } catch (Exception e) {
            log.warn("Could not fetch user preference: {}", e.getMessage());
            return null;
        }
    }

    private String buildAgentInput(String message, String userPreference) {
        if (userPreference != null && !userPreference.isEmpty()) {
            return String.format("User preference: %s\n\nUser question: %s", userPreference, message);
        }
        return message;
    }

    private String invokeBedrockAgent(String inputText) {
        try {
            log.info("Calling AWS Bedrock Agent API: {}", bedrockAgentApiUrl);
            
            // Prepare request body
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("message", inputText);
            
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);
            
            // Make the API call
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                bedrockAgentApiUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<Map<String, Object>>() {}
            );
            
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                Object replyObj = responseBody.get("reply");
                String reply = replyObj != null ? replyObj.toString() : "";
                log.info("Successfully received response from Bedrock Agent");
                return reply;
            } else {
                log.error("Bedrock Agent API returned non-success status: {}", response.getStatusCode());
                throw new RuntimeException("Failed to get response from Bedrock Agent");
            }
        } catch (Exception e) {
            log.error("Error calling Bedrock Agent API: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to invoke Bedrock Agent: " + e.getMessage(), e);
        }
    }

    private ChatResponse parseAgentResponse(String agentResponse) {
        try {
            // Extract natural language message
            int jsonStart = agentResponse.indexOf("JSON_RESULT_START");
            String naturalMessage = jsonStart > 0 ? agentResponse.substring(0, jsonStart).trim() : agentResponse;

            // Extract JSON
            if (jsonStart >= 0) {
                int jsonEnd = agentResponse.indexOf("JSON_RESULT_END", jsonStart);
                if (jsonEnd > jsonStart) {
                    String jsonStr = agentResponse.substring(jsonStart + "JSON_RESULT_START".length(), jsonEnd).trim();
                    ChatResponse.ProductRecommendation recommendation = objectMapper.readValue(
                            jsonStr, ChatResponse.ProductRecommendation.class);

                    return ChatResponse.builder()
                            .message(naturalMessage)
                            .recommendation(recommendation)
                            .build();
                }
            }

            // Fallback if no JSON found
            return ChatResponse.builder()
                    .message(naturalMessage)
                    .build();

        } catch (Exception e) {
            log.error("Error parsing agent response", e);
            return ChatResponse.builder()
                    .message(agentResponse)
                    .build();
        }
    }
}
