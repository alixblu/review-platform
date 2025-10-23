package com.example.postservice.mapper;

import com.example.postservice.dto.PostRequest;
import com.example.postservice.dto.PostResponse;
import com.example.postservice.model.Post;

import java.util.stream.Collectors;

public class PostMapper {
    public static PostResponse toDTO(Post post) {
        return new PostResponse(
                post.getId(),
                post.getUserId(),
                post.getProductId(),
                post.getContent(),
                post.getStatus(),
                post.getCreateAt(),
                post.getMediaList() != null
                        ? post.getMediaList().stream().toList()
                        : null,
                post.getEmbedding()
        );
    }

    public static PostRequest toDto(Post post) {
        return new PostRequest(
                post.getUserId(),
                post.getProductId(),
                post.getContent(),
                post.getMediaList(),
                post.getEmbedding()
        );
    }
}
