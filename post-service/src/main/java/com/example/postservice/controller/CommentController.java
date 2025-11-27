package com.example.postservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.comment.CommentCreationRequest;
import com.example.postservice.dto.comment.CommentResponse;
import com.example.postservice.dto.comment.CommentUpdateRequest;
import com.example.postservice.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Mapping chung
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {

    private final CommentService commentService;

    // 🟢 Create comment cho một bài post
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentCreationRequest request) {

        CommentResponse response = commentService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Comment created successfully", response));
    }

    // 🟡 Get all comments cho một bài post (có phân trang)
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<Page<CommentResponse>>> getCommentsForPost(
            @PathVariable Long postId,
            Pageable pageable) {

        Page<CommentResponse> responsePage = commentService.getCommentsForPost(postId, pageable);
        return ResponseEntity.ok(new ApiResponse<>("Comments retrieved successfully", responsePage));
    }

    // Update một comment cụ thể
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<CommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentUpdateRequest request) {

        CommentResponse response = commentService.updateComment(commentId, request);
        return ResponseEntity.ok(new ApiResponse<>("Comment updated successfully", response));
    }

    // Delete một comment cụ thể
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId) {

        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse<>("Comment deleted successfully", null));
    }
}
