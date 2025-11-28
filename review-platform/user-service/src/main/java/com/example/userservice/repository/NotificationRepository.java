package com.example.userservice.repository;

import com.example.userservice.model.Notification;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends Neo4jRepository<Notification, String> {
    List<Notification> findByUserId(String userId);
}
