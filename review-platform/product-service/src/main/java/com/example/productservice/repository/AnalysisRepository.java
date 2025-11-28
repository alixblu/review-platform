package com.example.productservice.repository;

import com.example.productservice.model.Analysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnalysisRepository extends JpaRepository<Analysis, UUID> {

    Optional<Analysis> findById(UUID id);

    List<Analysis> findAll();

    Optional<Analysis> findByProduct_Id(UUID productId);

    boolean existsByProduct_Id(UUID productId);

}
