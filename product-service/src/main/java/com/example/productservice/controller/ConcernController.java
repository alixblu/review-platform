package com.example.productservice.controller;

import com.example.productservice.dto.concern.ConcernResponse;
import com.example.productservice.dto.concern.ConcernUpdateRequest;
import com.example.productservice.service.ConcernService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/concerns")
@Tag(name = "Concern API")
public class ConcernController {

    private final ConcernService concernService;

    public ConcernController(ConcernService concernService) {
        this.concernService = concernService;
    }

    @GetMapping
    @Operation()
    public List<ConcernResponse> getAllConcerns() {
        return concernService.getAllConcerns();
    }

    @PutMapping("/{id}")
    @Operation()
    public ConcernResponse updateConcern(
            @PathVariable UUID id,
            @RequestBody ConcernUpdateRequest dto
    ) {
        return concernService.updateConcern(id, dto);
    }
}
