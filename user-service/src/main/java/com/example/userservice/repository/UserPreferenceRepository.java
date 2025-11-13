package com.example.userservice.repository;

import com.example.userservice.model.UserPreference;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPreferenceRepository extends Neo4jRepository<UserPreference, String> {

    Optional<UserPreference> findByUser_Id(String id);
}
