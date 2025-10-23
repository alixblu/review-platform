    package com.example.postservice.model;

    import com.pgvector.PGvector;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.UUID;

    @Entity
    @Table(name = "post")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public class Post {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(columnDefinition = "TEXT", nullable = false)
        private String content;

        @Column(name = "product_id")
        private UUID productId; // Foreign key to Product service

        @Column(name = "user_id", nullable = false)
        private UUID userId; // Foreign key to User service

        @ElementCollection
        @CollectionTable(name = "post_media", joinColumns = @JoinColumn(name = "post_id"))
        @Column(name = "url")
        private List<String> mediaList;

        @Column(name = "create_at", nullable = false)
        private LocalDateTime createAt = LocalDateTime.now();

        @Enumerated(EnumType.STRING)
        private Status status = Status.ACTIVE;

        @Column(columnDefinition = "vector(1536)")
        private PGvector embedding;

        public enum Status {
            ACTIVE, INACTIVE, DELETED
        }
    }
