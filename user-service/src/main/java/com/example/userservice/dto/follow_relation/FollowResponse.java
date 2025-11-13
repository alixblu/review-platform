package com.example.userservice.dto.follow_relation;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowResponse {
    private String id;
    private UUID followerId;
    private UUID followingId;
    private LocalDateTime createdAt;
}
