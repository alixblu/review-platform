package com.example.postservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.dto.post.PostUpdateRequest;
import com.example.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 🟢 Create new post (Đã có)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest) {
        PostResponse response = postService.createPost(postCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Post created successfully", response));
    }

    // 🟡 Get post by ID (Mới)
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable Long id) {
        PostResponse response = postService.getPostById(id);
        return ResponseEntity.ok(new ApiResponse<>("Post retrieved successfully", response));
    }

    // 🟡 Get all posts (Mới - Có phân trang)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAllPosts(Pageable pageable) {
        Page<PostResponse> responsePage = postService.getAllPosts(pageable);
        return ResponseEntity.ok(new ApiResponse<>("Posts retrieved successfully", responsePage));
    }

    // Update post
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostUpdateRequest updateRequest) {

        PostResponse response = postService.updatePost(id, updateRequest);
        return ResponseEntity.ok(new ApiResponse<>("Post updated successfully", response));
    }

    // Delete post
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {

        postService.deletePost(id);
        return ResponseEntity.ok(new ApiResponse<>("Post deleted successfully", null));
    }
}