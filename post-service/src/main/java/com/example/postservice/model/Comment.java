package com.example.postservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    @NotEmpty
    private String content;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    // (Đã loại bỏ trường updateAt)

    @Enumerated(EnumType.STRING)
    private Status status = Status.PUBLIC;

    public enum Status { PUBLIC, HIDDEN }

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
        // (Đã loại bỏ this.updateAt = LocalDateTime.now();)
        this.status = Status.PUBLIC;
    }

    // (Đã loại bỏ phương thức @PreUpdate)
}