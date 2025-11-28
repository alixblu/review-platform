package com.example.postservice.repository;

import com.example.postservice.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(UUID userId);

    List<Post> findByProductId(UUID productId);

    List<Post> findByStatus(String status);

//    @Query(value = "SELECT * FROM post ORDER BY embedding <-> CAST(:embedding AS vector) LIMIT :limit", nativeQuery = true)
//    List<Post> findNearestPosts(float[] embedding, int limit);

}
