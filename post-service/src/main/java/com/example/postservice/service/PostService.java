package com.example.postservice.service;

import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.dto.post.PostUpdateRequest;
import com.example.postservice.mapper.PostMapper;
import com.example.postservice.model.Post;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// <-- Thêm import cho Phân trang -->
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List; // <-- Import này có thể cần cho `getAllPosts` nếu không phân trang

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    // 🟢 Create (Đã có)
    @Transactional
    public PostResponse createPost(PostCreationRequest postCreationRequest) {
        Post post = postMapper.toModel(postCreationRequest);
        postRepository.save(post);
        log.info("Post created with id: {}", post.getId());
        return postMapper.toResponse(post);
    }

    // Update
    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest updateRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        if (updateRequest.content() != null) {
            post.setContent(updateRequest.content());
        }
        if (updateRequest.mediaList() != null) {
            post.setMediaList(updateRequest.mediaList());
        }
        if (updateRequest.status() != null) {
            post.setStatus(updateRequest.status());
        }

        Post updatedPost = postRepository.save(post);
        log.info("Post updated with id: {}", updatedPost.getId());
        return postMapper.toResponse(updatedPost);
    }

    // Delete
    @Transactional
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
        log.info("Post deleted with id: {}", id);
    }

    // 🟡 Get By Id
    @Transactional(readOnly = true)
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return postMapper.toResponse(post);
    }

    // 🟡 Get All
    @Transactional(readOnly = true)
    public Page<PostResponse> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        // Dùng .map() của Page để chuyển đổi Page<Post> thành Page<PostResponse>
        return postPage.map(postMapper::toResponse);
    }
}