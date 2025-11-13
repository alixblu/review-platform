package com.example.userservice.repository;

import com.example.userservice.model.FollowRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FollowRelationRepository extends Neo4jRepository<FollowRelation, String> {

    List<FollowRelation> findByFollowerId(String followerId);

    List<FollowRelation> findByFollowingId(String followingId);

    Optional<FollowRelation> findByFollowerIdAndFollowingId(String followerId, UUID followingId);
}
