package com.example.productservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "analysis")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Analysis {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, columnDefinition = "uuid")
    private Product product;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "analysis_high_risk", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "value", columnDefinition = "TEXT")
    private List<String> highRisk;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "analysis_avg_risk", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "value", columnDefinition = "TEXT")
    private List<String> avgRisk;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "analysis_low_risk", joinColumns = @JoinColumn(name = "analysis_id"))
    @Column(name = "value", columnDefinition = "TEXT")
    private List<String> lowRisk;
}
