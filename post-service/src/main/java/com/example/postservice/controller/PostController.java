package com.example.postservice.controller;


import com.example.commonlib.dto.ApiResponse;
import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 🟢 Create new post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody @Valid PostCreationRequest postCreationRequest) {

        PostResponse response = postService.createPost(postCreationRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Post created successfully", response));
    }
}
