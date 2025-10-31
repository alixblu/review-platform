package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends Neo4jRepository<User,String> {
    boolean existsUserByAccId(UUID accId);

    default boolean findUserById(String id) {
        return false;
    }
}
