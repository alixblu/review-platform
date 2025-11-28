package com.example.productservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
import com.example.productservice.dto.brand.BrandCreationRequest;
import com.example.productservice.dto.brand.BrandResponse;
import com.example.productservice.mapper.BrandMapper;
import com.example.productservice.model.Brand;
import com.example.productservice.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.productservice.dto.brand.BrandUpdateRequest;


import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    /**
     * Tạo mới thương hiệu
     */
    public BrandResponse createBrand(BrandCreationRequest request) {
        if (brandRepository.existsByName(request.name())) {
            throw new AppException(ErrorCode.EXISTED, "Brand already exists");
        }

        // Chuyển từ DTO → Entity
        Brand brand = brandMapper.toModel(request);

        brandRepository.save(brand);
        log.info("Created brand successfully: {}", brand.getName());

        // Entity → DTO Response
        return brandMapper.toResponse(brand);
    }

    /**
     * Lấy danh sách tất cả thương hiệu
     */
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    /**
     * Lấy thương hiệu theo ID
     */
    public BrandResponse getBrandById(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Brand not found"));
        return brandMapper.toResponse(brand);
    }

//    /**
//     * Cập nhật thương hiệu
//     */
//    public BrandResponse updateBrand(UUID id, BrandCreationRequest request) {
//        Brand existingBrand = brandRepository.findById(id)
//                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Brand not found"));
//
//        existingBrand.setName(request.name());
//        existingBrand.setDescription(request.description());
//        existingBrand.setWebsite(request.website());
//        existingBrand.setLogoUrl(request.logoUrl());
//        existingBrand.setCountry(request.country());
//
//        brandRepository.save(existingBrand);
//        log.info("Updated brand successfully: {}", existingBrand.getName());
//
//        return brandMapper.toResponse(existingBrand);
//    }

    /**
     * Xóa thương hiệu
     */
    public void deleteBrand(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Brand not found"));
        brandRepository.delete(brand);
        log.info("Deleted brand successfully: {}", brand.getName());
    }

    public BrandResponse updateBrand(UUID id, BrandUpdateRequest request) {
        Brand existingBrand = brandRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Brand not found"));

        // Dùng mapper để cập nhật các field không null
        brandMapper.updateBrandFromRequest(request, existingBrand);

        brandRepository.save(existingBrand);
        log.info("Updated brand successfully: {}", existingBrand.getName());

        return brandMapper.toResponse(existingBrand);
    }


}