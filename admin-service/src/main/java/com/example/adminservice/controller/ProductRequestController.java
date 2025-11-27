package com.example.adminservice.controller;

import com.example.adminservice.dto.request.ProductRequestCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ProductRequestResponse;
import com.example.adminservice.model.enums.RequestStatus;
import com.example.adminservice.service.ProductRequestService;
import com.example.commonlib.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-requests")
@RequiredArgsConstructor
@Slf4j
public class ProductRequestController {

    private final ProductRequestService productRequestService;


    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ApiResponse<ProductRequestResponse> createProductRequest(
            @Valid @RequestBody ProductRequestCreationRequest request) {
        log.info("Request to create product request for link: {}", request.getOfficialLink());
        ProductRequestResponse response = productRequestService.createProductRequest(request);
        return ApiResponse.<ProductRequestResponse>builder()
                .code(1000) // hoặc code tùy ý
                .message("Product request created successfully")
                .result(response)
                .build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ProductRequestResponse>> getAllProductRequests(
            @RequestParam(required = false) RequestStatus status) {
        log.info("Request to get all product requests with status: {}", status);
        List<ProductRequestResponse> responses = productRequestService.getAllProductRequests(status);
        return ApiResponse.<List<ProductRequestResponse>>builder()
                .code(1000)
                .message("Fetched product requests successfully")
                .result(responses)
                .build();
    }

    @GetMapping("/{requestId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductRequestResponse> getProductRequestById(
            @PathVariable UUID requestId) {
        log.info("Request to get product request by id: {}", requestId);
        ProductRequestResponse response = productRequestService.getProductRequest(requestId);
        return ApiResponse.<ProductRequestResponse>builder()
                .code(1000)
                .message("Fetched product request successfully")
                .result(response)
                .build();
    }

    @PutMapping("/{requestId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<ProductRequestResponse> updateProductRequestStatus(
            @PathVariable UUID requestId,
            @Valid @RequestBody RequestStatusUpdateRequest request) {
        log.info("Request to update status for productRequestId: {}", requestId);
        ProductRequestResponse response = productRequestService.updateProductRequestStatus(requestId, request);
        return ApiResponse.<ProductRequestResponse>builder()
                .code(1000)
                .message("Product request status updated")
                .result(response)
                .build();
    }
}