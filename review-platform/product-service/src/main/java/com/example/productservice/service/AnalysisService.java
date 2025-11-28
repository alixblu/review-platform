package com.example.productservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.dto.analysis.AnalysisCreationRequest;
import com.example.productservice.dto.analysis.AnalysisResponse;
import com.example.productservice.mapper.AnalysisMapper;
import com.example.productservice.model.Analysis;
import com.example.productservice.model.Product;
import com.example.productservice.repository.AnalysisRepository;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AnalysisRepository analysisRepository;
    private final ProductRepository productRepository;
    private final AnalysisMapper analysisMapper;

    public AnalysisResponse createAnalysis(AnalysisCreationRequest request) {
        // Kiểm tra product có tồn tại không
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Product not found"));

        // Kiểm tra đã có analysis cho product này chưa
        if (analysisRepository.findByProduct_Id(product.getId()).isPresent()) {
            throw new AppException(ErrorCode.EXISTED, "Analysis already exists for this product");
        }

        // Map DTO → Entity
        Analysis analysis = analysisMapper.toModel(request);
        analysis.setProduct(product);

        analysisRepository.save(analysis);
        log.info("Created analysis successfully for product: {}", product.getName());

        // Map Entity → Response
        return analysisMapper.toResponse(analysis);
    }
    public AnalysisResponse updateAnalysis(UUID productId, AnalysisCreationRequest request) {
        Analysis analysis = analysisRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Không tìm thấy Analysis cho sản phẩm này"));

        // Cập nhật dữ liệu
        analysis.setHighRisk(request.highRisk());
        analysis.setAvgRisk(request.avgRisk());
        analysis.setLowRisk(request.lowRisk());

        analysisRepository.save(analysis);
        log.info("Updated analysis successfully for product: {}", productId);

        return analysisMapper.toResponse(analysis);
    }

    public List<AnalysisResponse> getAllAnalyses() {
        List<Analysis> analyses = analysisRepository.findAll();
        return analyses.stream()
                .map(analysisMapper::toResponse)
                .toList();
    }

    public AnalysisResponse getAnalysisByProductId(UUID productId) {
        Analysis analysis = analysisRepository.findByProduct_Id(productId)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Analysis not found for this product"));
        return analysisMapper.toResponse(analysis);
    }
}
