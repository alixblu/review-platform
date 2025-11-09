package com.example.postservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.like.LikeToggleResponse;
import com.example.postservice.dto.like.PostLikeRequest;
import com.example.postservice.service.PostLikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/posts") // Gắn vào /api/posts
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    // 🟢 Toggle Like/Unlike
    @PostMapping("/{postId}/toggle-like")
    public ResponseEntity<ApiResponse<LikeToggleResponse>> togglePostLike(
            @PathVariable Long postId,
            @RequestBody @Valid PostLikeRequest request) {

        LikeToggleResponse response = postLikeService.toggleLike(postId, request.userId());
        String message = response.liked() ? "Post liked successfully" : "Post unliked successfully";

        return ResponseEntity.ok(new ApiResponse<>(message, response));
    }
}