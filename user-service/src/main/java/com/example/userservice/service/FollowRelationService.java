package com.example.userservice.service;

import com.example.userservice.dto.follow_relation.FollowRequest;
import com.example.userservice.dto.follow_relation.FollowResponse;
import com.example.userservice.mapper.FollowRelationMapper;
import com.example.userservice.model.FollowRelation;
import com.example.userservice.repository.FollowRelationRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowRelationService {

    final FollowRelationRepository followRelationRepository;
    final FollowRelationMapper followRelationMapper;

    public FollowResponse follow(FollowRequest request) {
        if (followRelationRepository.findByFollowerIdAndFollowingId(request.getFollowerId(), request.getFollowingId()).isPresent()) {
            throw new RuntimeException("Already following this user");
        }
        FollowRelation relation = followRelationMapper.toModel(request);
        FollowRelation saved = followRelationRepository.save(relation);
        return followRelationMapper.toResponse(saved);
    }

    public void unfollow(FollowRequest request) {
        FollowRelation relation = followRelationRepository.findByFollowerIdAndFollowingId(request.getFollowerId(), request.getFollowingId())
                .orElseThrow(() -> new RuntimeException("Follow relation not found"));
        followRelationRepository.delete(relation);
    }

    public List<FollowResponse> getFollowers(String userId) {
        return followRelationRepository.findByFollowingId(UUID.fromString(userId))
                .stream().map(followRelationMapper::toResponse).collect(Collectors.toList());
    }

    public List<FollowResponse> getFollowing(String userId) {
        return followRelationRepository.findByFollowerId(UUID.fromString(userId))
                .stream().map(followRelationMapper::toResponse).collect(Collectors.toList());
    }
}
