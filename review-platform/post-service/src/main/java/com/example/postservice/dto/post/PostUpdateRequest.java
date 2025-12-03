package com.example.postservice.dto.post;

import com.example.postservice.model.Post;
import jakarta.validation.constraints.Size;
import java.util.List;

public record PostUpdateRequest(
        @Size(max = 1000, message = "Content must not exceed 1000 characters")
        String content,

        @Size(max = 3, message = "You can upload up to 3 media items")
        List<String> mediaUrls,

        Post.Status status
) {}