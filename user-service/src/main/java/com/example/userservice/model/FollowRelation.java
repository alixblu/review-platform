package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Node("FollowRelation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRelation {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("follower_id")
    private UUID followerId;

    @Property("following_id")
    private UUID followingId;

    @Property("created_at")
    private LocalDateTime createdAt;
}
