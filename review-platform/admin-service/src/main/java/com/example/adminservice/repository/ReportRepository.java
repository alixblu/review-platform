package com.example.adminservice.repository;

import com.example.adminservice.model.Report;
import com.example.adminservice.model.enums.ReportContentType;
import com.example.adminservice.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByStatus(RequestStatus status);

    List<Report> findByContentType(ReportContentType contentType);
}