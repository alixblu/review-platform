package com.example.postservice.repository;

import com.example.postservice.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    // Tìm một like cụ thể bằng postId và userId
    Optional<PostLike> findByPostIdAndUserId(Long postId, UUID userId);

    // Đếm số like cho một post
    long countByPostId(Long postId);
}
