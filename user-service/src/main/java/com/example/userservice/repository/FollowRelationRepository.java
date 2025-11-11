package com.example.userservice.repository;

import com.example.userservice.model.FollowRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRelationRepository extends Neo4jRepository<FollowRelation, String> {

    List<FollowRelation> findByFollowerId(UUID followerId);

    List<FollowRelation> findByFollowingId(UUID followingId);

    Optional<FollowRelation> findByFollowerIdAndFollowingId(UUID followerId, UUID followingId);
}
