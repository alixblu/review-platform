package com.example.authservice.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${user-service.url:http://localhost:8081/api}")
public interface UserClient {
    @PostMapping(value = "internal/user", produces = MediaType.APPLICATION_JSON_VALUE)
    Object createUser (@RequestBody UserCreationRequest request);

    @GetMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUser (@PathVariable String id);

    @GetMapping (value = "/user/acc/{accId}", produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUserByAccId (@PathVariable String accId);
}
