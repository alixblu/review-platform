package com.example.productservice.repository;

import com.example.productservice.model.Concern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConcernRepository extends JpaRepository<Concern, UUID> {
}
