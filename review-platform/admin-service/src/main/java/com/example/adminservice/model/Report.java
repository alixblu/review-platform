package com.example.adminservice.model;

import com.example.adminservice.model.enums.ReportContentType;
import com.example.adminservice.model.enums.ReportType;
import com.example.adminservice.model.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "report")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "content_id")
    private UUID contentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type")
    private ReportContentType contentType;

    @Column(name = "user_report")
    private UUID userReport; // ID của user đã report

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReportType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @CreationTimestamp
    @Column(name = "create_at", updatable = false)
    private LocalDateTime createAt;
}