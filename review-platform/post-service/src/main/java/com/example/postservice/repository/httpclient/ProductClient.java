package com.example.postservice.repository.httpclient;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.httpclient.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service", url = "${PRODUCT_SERVICE_URL:http://localhost:8082/api/product}")
public interface ProductClient {

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    ApiResponse<List<ProductResponse>> getAllProducts();
}
