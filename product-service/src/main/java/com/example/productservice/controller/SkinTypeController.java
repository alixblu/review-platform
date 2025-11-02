package com.example.productservice.controller;

import com.example.productservice.dto.skintype.SkinTypeResponse;
import com.example.productservice.dto.skintype.SkinTypeUpdateRequest;
import com.example.productservice.mapper.SkinTypeMapper;
import com.example.productservice.model.SkinType;
import com.example.productservice.service.SkinTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/skin-types")
@CrossOrigin("*")
@RequiredArgsConstructor
public class SkinTypeController {

    private final SkinTypeService service;
    private final SkinTypeMapper mapper;

    // READ ALL
    @GetMapping
    public List<SkinTypeResponse> getAll() {
        return service.getAll().stream()
                .map(mapper::toReadDTO)
                .collect(Collectors.toList());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<SkinTypeResponse> getById(@PathVariable UUID id) {
        return service.getById(id)
                .map(mapper::toReadDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<SkinTypeResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody SkinTypeUpdateRequest dto) {

        return service.getById(id)
                .map(existing -> {
                    mapper.updateEntityFromDTO(dto, existing);
                    SkinType saved = service.update(id, existing);
                    return ResponseEntity.ok(mapper.toReadDTO(saved));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
