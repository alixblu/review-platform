package com.example.postservice.service;

import com.example.postservice.dto.like.LikeToggleResponse;
import com.example.postservice.model.Post;
import com.example.postservice.model.PostLike;
import com.example.postservice.repository.PostLikeRepository;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional
    public LikeToggleResponse toggleLike(Long postId, UUID userId) {
        // 1. Kiểm tra Post có tồn tại không
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // 2. Kiểm tra xem người dùng đã like bài post này chưa
        Optional<PostLike> existingLike = postLikeRepository.findByPostIdAndUserId(postId, userId);

        boolean liked;

        if (existingLike.isPresent()) {
            // 3a. Nếu đã like -> Xóa (Unlike)
            postLikeRepository.delete(existingLike.get());
            log.info("User {} unliked post {}", userId, postId);
            liked = false;
        } else {
            // 3b. Nếu chưa like -> Tạo mới (Like)
            PostLike newLike = PostLike.builder()
                    .post(post)
                    .userId(userId)
                    .build();
            postLikeRepository.save(newLike);
            log.info("User {} liked post {}", userId, postId);
            liked = true;
        }

        // 4. Đếm lại tổng số like mới và trả về
        long newLikeCount = postLikeRepository.countByPostId(postId);
        return new LikeToggleResponse(liked, newLikeCount);
    }
}