package com.example.productservice.service;

import com.example.productservice.dto.skintype.SkinTypeResponse;
import com.example.productservice.dto.skintype.SkinTypeUpdateRequest;
import com.example.productservice.mapper.SkinTypeMapper;
import com.example.productservice.model.SkinType;
import com.example.productservice.repository.SkinTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkinTypeService {

    private final SkinTypeRepository repository;
    private final SkinTypeMapper mapper;

    // READ all
    public List<SkinTypeResponse> getAllSkinTypes() {
        return repository.findAll()
                .stream()
                .map(mapper::toReadDTO)
                .toList();
    }

    // UPDATE
    public SkinTypeResponse updateSkinType(UUID id, SkinTypeUpdateRequest dto) {
        SkinType existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found with id: " + id));

        mapper.updateEntityFromDTO(dto, existing);
        repository.save(existing);

        return mapper.toReadDTO(existing);
    }
}
