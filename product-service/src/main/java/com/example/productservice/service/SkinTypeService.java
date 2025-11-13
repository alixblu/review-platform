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

    // ✅ READ all
    public List<SkinTypeResponse> getAllSkinTypes() {
        return repository.findAll()
                .stream()
                .map(mapper::toReadDTO)
                .toList();
    }

    // ✅ READ by id
    public SkinTypeResponse getSkinTypeById(UUID id) {
        SkinType skinType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found with id: " + id));
        return mapper.toReadDTO(skinType);
    }

    // ✅ UPDATE
    public SkinTypeResponse updateSkinType(UUID id, SkinTypeUpdateRequest dto) {
        SkinType skinType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found with id: " + id));

        mapper.updateEntityFromDTO(dto, skinType);
        SkinType updated = repository.save(skinType);

        return mapper.toReadDTO(updated);
    }
}
