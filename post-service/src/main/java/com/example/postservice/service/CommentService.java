package com.example.postservice.service;

import com.example.postservice.dto.comment.CommentCreationRequest;
import com.example.postservice.dto.comment.CommentResponse;
import com.example.postservice.dto.comment.CommentUpdateRequest;
import com.example.postservice.mapper.CommentMapper;
import com.example.postservice.model.Comment;
import com.example.postservice.model.Post;
import com.example.postservice.repository.CommentRepository;
import com.example.postservice.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository; // Cần để tìm Post
    private final CommentMapper commentMapper;

    // 🟢 Create
    @Transactional
    public CommentResponse createComment(Long postId, CommentCreationRequest request) {
        // 1. Tìm Post cha
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // 2. Map DTO -> Model
        Comment comment = commentMapper.toModel(request);

        // 3. Liên kết comment với post
        comment.setPost(post);

        // 4. Lưu comment
        Comment savedComment = commentRepository.save(comment);
        log.info("Comment created with id: {} for post id: {}", savedComment.getId(), postId);
        return commentMapper.toResponse(savedComment);
    }

    // 🟡 Get All (cho 1 bài post, có phân trang)
    @Transactional(readOnly = true)
    public Page<CommentResponse> getCommentsForPost(Long postId, Pageable pageable) {
        // Kiểm tra xem Post có tồn tại không
        if (!postRepository.existsById(postId)) {
            throw new RuntimeException("Post not found with id: " + postId);
        }

        Page<Comment> commentPage = commentRepository.findAllByPostId(postId, pageable);
        return commentPage.map(commentMapper::toResponse);
    }

    // Update
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));

        if (request.content() != null) {
            comment.setContent(request.content());
        }
        // Cập nhật status
        if (request.status() != null) {
            comment.setStatus(request.status());
        }

        comment.setContent(request.content());
        Comment updatedComment = commentRepository.save(comment);
        log.info("Comment updated with id: {}", updatedComment.getId());
        return commentMapper.toResponse(updatedComment);
    }

    // Delete
    @Transactional
    public void deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new RuntimeException("Comment not found with id: " + commentId);
        }

        // (Thực tế nên kiểm tra quyền sở hữu trước khi xóa)

        commentRepository.deleteById(commentId);
        log.info("Comment deleted with id: {}", commentId);
    }
}