package com.example.adminservice.service;

import com.example.adminservice.dto.request.ProductRequestCreationRequest;
import com.example.adminservice.dto.request.RequestStatusUpdateRequest;
import com.example.adminservice.dto.response.ProductRequestResponse;
import com.example.adminservice.mapper.ProductRequestMapper;
import com.example.adminservice.model.ProductRequest;
import com.example.adminservice.model.enums.RequestStatus;
import com.example.adminservice.repository.ProductRequestRepository;
import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRequestService {

    private final ProductRequestRepository productRequestRepository;
    private final ProductRequestMapper productRequestMapper;

    /**
     * Tạo một yêu cầu sản phẩm mới (do user thực hiện).
     */
    @Transactional
    public ProductRequestResponse createProductRequest(ProductRequestCreationRequest request) {
        log.info("Creating new product request for link: {}", request.getOfficialLink());

        // Lấy userId từ thông tin xác thực
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        UUID userId = UUID.fromString(userIdStr);

        ProductRequest productRequest = productRequestMapper.toProductRequest(request);
        productRequest.setUserId(userId);
        productRequest.setStatus(RequestStatus.UnderReview); // Trạng thái mặc định

        ProductRequest savedRequest = productRequestRepository.save(productRequest);
        return productRequestMapper.toProductRequestResponse(savedRequest);
    }

    /**
     * Cập nhật trạng thái của một yêu cầu sản phẩm (do admin thực hiện).
     */
    @Transactional
    public ProductRequestResponse updateProductRequestStatus(UUID requestId, RequestStatusUpdateRequest request) {
        log.info("Admin updating status for productRequestId: {}", requestId);

        ProductRequest existingRequest = findProductRequestById(requestId);
        productRequestMapper.updateProductRequestFromDto(request, existingRequest);

        ProductRequest updatedRequest = productRequestRepository.save(existingRequest);
        return productRequestMapper.toProductRequestResponse(updatedRequest);
    }

    /**
     * Lấy tất cả yêu cầu sản phẩm, có thể lọc theo trạng thái.
     */
    public List<ProductRequestResponse> getAllProductRequests(RequestStatus status) {
        List<ProductRequest> requests;
        if (status != null) {
            log.info("Fetching all product requests with status: {}", status);
            requests = productRequestRepository.findByStatus(status);
        } else {
            log.info("Fetching all product requests");
            requests = productRequestRepository.findAll();
        }

        return requests.stream()
                .map(productRequestMapper::toProductRequestResponse)
                .collect(Collectors.toList());
    }

    /**
     * Lấy chi tiết một yêu cầu sản phẩm.
     */
    public ProductRequestResponse getProductRequest(UUID requestId) {
        log.info("Fetching product request details for requestId: {}", requestId);
        ProductRequest productRequest = findProductRequestById(requestId);
        return productRequestMapper.toProductRequestResponse(productRequest);
    }

    // --- Helper method ---
    private ProductRequest findProductRequestById(UUID requestId) {
        return productRequestRepository.findById(requestId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "ProductRequest not found with id: " + requestId));
    }
}