package com.example.productservice.service;

import com.example.productservice.model.SkinType;
import com.example.productservice.repository.SkinTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SkinTypeService {

    private final SkinTypeRepository repository;

    public List<SkinType> getAll() {
        return repository.findAll();
    }

    public Optional<SkinType> getById(UUID id) {
        return repository.findById(id);
    }

    public SkinType update(UUID id, SkinType updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setType(updated.getType());
                    existing.setDescription(updated.getDescription());
                    existing.setAvoid(updated.getAvoid());
                    existing.setRecommend(updated.getRecommend());
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("SkinType not found with id: " + id));
    }
}
