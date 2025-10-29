package com.example.productservice.model;
import com.example.commonlib.enums.ConcernTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "concern")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Concern {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private ConcernTypeEnum type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String avoid;

    @Column(columnDefinition = "TEXT")
    private String recommend;
}
