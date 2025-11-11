package com.example.userservice.dto.follow_relation;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowRequest {

    @NotNull(message = "Follower ID is required")
    private UUID followerId;

    @NotNull(message = "Following ID is required")
    private UUID followingId;
}
