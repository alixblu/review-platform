package com.example.productservice.repository;

import com.example.productservice.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

    Optional<Brand> findById(UUID id);

    List<Brand> findAll();

    Boolean existsByName(String name);
}