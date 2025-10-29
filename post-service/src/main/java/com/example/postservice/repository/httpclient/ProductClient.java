package com.example.postservice.repository.httpclient;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.httpclient.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8080/api/product")
public interface ProductClient {

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<ProductResponse>> getAllProducts();
}
