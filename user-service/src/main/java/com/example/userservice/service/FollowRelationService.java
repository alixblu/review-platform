package com.example.userservice.service;

import com.example.commonlib.exception.AppException;
import com.example.commonlib.exception.ErrorCode;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowRelationService {

    final FollowRelationRepository followRelationRepository;
    final FollowRelationMapper followRelationMapper;

    public FollowResponse follow(FollowRequest request) {
        boolean exists = followRelationRepository
                .findByFollowerIdAndFollowingId(
                        String.valueOf(request.getFollowerId()),
                        request.getFollowingId()
                )
                .isPresent();

        if (exists) {
            throw new AppException(ErrorCode.EXISTED, "You are already following this user");
        }

        FollowRelation relation = followRelationMapper.toModel(request);
        FollowRelation saved = followRelationRepository.save(relation);
        return followRelationMapper.toResponse(saved);
    }

    public void unfollow(FollowRequest request) {
        FollowRelation relation = followRelationRepository
                .findByFollowerIdAndFollowingId(
                        String.valueOf(request.getFollowerId()),
                        request.getFollowingId()
                )
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND, "Follow relation not found"));

        followRelationRepository.delete(relation);
    }

    public List<FollowResponse> getFollowers(String userId) {
        return followRelationRepository.findByFollowingId(userId)
                .stream().map(followRelationMapper::toResponse).collect(Collectors.toList());
    }

    public List<FollowResponse> getFollowing(String userId) {
        return followRelationRepository.findByFollowerId(userId)
                .stream().map(followRelationMapper::toResponse).collect(Collectors.toList());
    }
}
