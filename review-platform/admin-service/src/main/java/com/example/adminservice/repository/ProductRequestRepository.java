package com.example.adminservice.repository;

import com.example.adminservice.model.ProductRequest;
import com.example.adminservice.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRequestRepository extends JpaRepository<ProductRequest, UUID> {

    List<ProductRequest> findByStatus(RequestStatus status);
}