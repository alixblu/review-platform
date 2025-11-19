package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User,String> {
    boolean existsUserByAccId(String accId);
    Optional<User> findUserById(String id);
    Optional<User> findUserByAccId(String accId);
}