package com.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Slf4j
public class S3TestController {

    private final S3Client knowledgeBaseS3Client;

    @Value("${s3.knowledgebase.bucket}")
    private String knowledgeBaseBucket;

    public S3TestController(@Qualifier("knowledgeBaseS3Client") S3Client knowledgeBaseS3Client) {
        this.knowledgeBaseS3Client = knowledgeBaseS3Client;
    }

    @GetMapping("/s3-access")
    public Map<String, Object> testS3Access() {
        Map<String, Object> result = new HashMap<>();
        result.put("bucket", knowledgeBaseBucket);
        
        try {
            // Test 1: List objects
            log.info("Testing ListObjects on bucket: {}", knowledgeBaseBucket);
            var listResponse = knowledgeBaseS3Client.listObjectsV2(
                    ListObjectsV2Request.builder()
                            .bucket(knowledgeBaseBucket)
                            .maxKeys(1)
                            .build()
            );
            result.put("listObjects", "SUCCESS - Found " + listResponse.keyCount() + " objects");
            log.info("ListObjects SUCCESS");
            
            // Test 2: Put object
            String testKey = "test/access-test.txt";
            log.info("Testing PutObject on bucket: {}, key: {}", knowledgeBaseBucket, testKey);
            knowledgeBaseS3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(knowledgeBaseBucket)
                            .key(testKey)
                            .contentType("text/plain")
                            .build(),
                    RequestBody.fromString("Test access at " + System.currentTimeMillis())
            );
            result.put("putObject", "SUCCESS");
            result.put("status", "All tests passed!");
            log.info("PutObject SUCCESS");
            
        } catch (Exception e) {
            log.error("S3 access test failed", e);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            result.put("errorClass", e.getClass().getName());
            result.put("status", "FAILED");
            
            if (e.getCause() != null) {
                result.put("cause", e.getCause().getMessage());
            }
        }
        
        return result;
    }
}
