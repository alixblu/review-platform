package com.example.postservice.service;

import com.example.postservice.dto.PostRequest;
import com.example.postservice.dto.PostResponse;
import com.example.postservice.mapper.PostMapper;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public PostResponse createPost(PostRequest postRequest) {
        Post post = Post.builder()
                .content(postRequest.content())
                .productId(postRequest.productId())
                .userId(postRequest.userId())
                .mediaList(postRequest.mediaList())
                .status(Post.Status.ACTIVE)
                .createAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        log.info("Post created");
        return PostMapper.toDTO(post);//this func return postresponse
    }
}
