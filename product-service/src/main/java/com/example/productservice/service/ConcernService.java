package com.example.productservice.service;

import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.dto.concern.ConcernUpdateRequest;
import com.example.productservice.mapper.ConcernMapper;
import com.example.productservice.model.Concern;
import com.example.productservice.repository.ConcernRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConcernService {

    private final ConcernRepository concernRepository;
    private final ConcernMapper concernMapper;

    public ConcernService(ConcernRepository concernRepository, ConcernMapper concernMapper) {
        this.concernRepository = concernRepository;
        this.concernMapper = concernMapper;
    }

    // READ all
    public List<ConcernResponse> getAllConcerns() {
        return concernRepository.findAll()
                .stream()
                .map(concernMapper::toReadDTO)
                .toList();
    }

    // UPDATE
    public ConcernResponse updateConcern(UUID id, ConcernUpdateRequest dto) {
        Concern concern = concernRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concern not found"));

        concern.setType(dto.getType());
        concern.setDescription(dto.getDescription());
        concern.setAvoid(dto.getAvoid());
        concern.setRecommend(dto.getRecommend());

        Concern updated = concernRepository.save(concern);
        return concernMapper.toReadDTO(updated);
    }
}
