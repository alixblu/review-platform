package com.example.postservice.controller;

import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 🟢 Create new post
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@RequestBody PostCreationRequest postCreationRequest) {
        return postService.createPost(postCreationRequest);

    }
}
