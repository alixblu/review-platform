package com.example.postservice.repository;

import com.example.postservice.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Tự động tạo query để tìm tất cả Comment theo post_id, có phân trang
    Page<Comment> findAllByPostId(Long postId, Pageable pageable);
}