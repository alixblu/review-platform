package com.example.postservice.service;

import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.mapper.PostMapper;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PostResponse createPost(PostCreationRequest postCreationRequest) {
        Post post = postMapper.toModel(postCreationRequest);
        postRepository.save(post);
        log.info("Post created");
        return postMapper.toResponse(post);//this func return postresponse
    }
}
