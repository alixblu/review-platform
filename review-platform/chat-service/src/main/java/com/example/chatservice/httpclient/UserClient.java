package com.example.chatservice.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${service.user.url:http://localhost:8081}")
public interface UserClient {
    
    @GetMapping(value = "/api/user/preference/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUserPreference(@PathVariable String userId);
}
