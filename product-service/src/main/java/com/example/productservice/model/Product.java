package com.example.productservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter

public class Product {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

//    @Column(nullable = false, columnDefinition = "uuid")
    @Column(nullable = true, columnDefinition = "uuid")
    private UUID brand; // External reference, not enforced

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String ingredients;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection(targetClass = ConcernTypeEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "product_concern", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "concern_type")
    private List<ConcernTypeEnum> concernTypes;

    @ElementCollection(targetClass = SkinTypeEnum.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "product_skintype", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "skin_type")
    private List<SkinTypeEnum> skinTypes;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Min(1000)
    @Column(nullable = false)
    private long price;

    @Column(columnDefinition = "FLOAT DEFAULT 0.0", nullable = false)
    private Float rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;
}