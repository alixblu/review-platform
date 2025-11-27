    package com.example.postservice.model;

    import jakarta.persistence.*;
    import jakarta.validation.constraints.Null;
    import lombok.*;
    import com.pgvector.PGvector;
    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.UUID;
    import org.hibernate.annotations.Formula;

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
        private UUID productId;

        @Column(name = "user_id", nullable = false)
        private UUID userId;

        @ElementCollection
        @CollectionTable(name = "post_media", joinColumns = @JoinColumn(name = "post_id"))
        @Column(name = "url")
        private List<String> mediaUrls;

        @Column(name = "create_at", nullable = false, updatable = false)
        private LocalDateTime createAt;

        @Enumerated(EnumType.STRING)
        private Status status = Status.PUBLIC;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<Comment> comments;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<PostLike> likes;

        // Tự động đếm số like bằng câu lệnh SQL
        @Formula("(select count(1) from post_like pl where pl.post_id = id)")
        private int likeCount;

        public enum Status { PUBLIC, HIDDEN }

        @PrePersist
        protected void onCreate() {
            this.createAt = LocalDateTime.now() ;
            this.status = Status.PUBLIC;
        }
    }
