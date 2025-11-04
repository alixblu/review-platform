package com.example.productservice.service;

import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.dto.concern.ConcernUpdateRequest;
import com.example.productservice.mapper.ConcernMapper;
import com.example.productservice.model.Concern;
import com.example.productservice.repository.ConcernRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConcernService {

    private final ConcernRepository repository;
    private final ConcernMapper mapper;

    // READ all
    public List<ConcernResponse> getAllConcerns() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // UPDATE
    public ConcernResponse updateConcern(UUID id, ConcernUpdateRequest dto) {
        Concern existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concern not found with id: " + id));

        mapper.updateEntityFromDTO(dto, existing);
        Concern updated = repository.save(existing);

        return mapper.toResponse(updated);
    }
}
