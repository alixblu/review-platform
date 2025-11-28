package com.example.userservice.controller;

import com.example.commonlib.dto.ApiResponse;
import com.example.userservice.dto.follow_relation.FollowRequest;
import com.example.userservice.dto.follow_relation.FollowResponse;
import com.example.userservice.service.FollowRelationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follow")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FollowRelationController {

    FollowRelationService followRelationService;

    @PostMapping
    public ResponseEntity<ApiResponse<FollowResponse>> follow(@RequestBody FollowRequest request) {
        FollowResponse response = followRelationService.follow(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Followed user successfully", response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> unfollow(@RequestBody FollowRequest request) {
        followRelationService.unfollow(request);
        return ResponseEntity.ok(new ApiResponse<>("Unfollowed user successfully", null));
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowers(@PathVariable String userId) {
        List<FollowResponse> list = followRelationService.getFollowers(userId);
        return ResponseEntity.ok(new ApiResponse<>("Fetched followers successfully", list));
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<ApiResponse<List<FollowResponse>>> getFollowing(@PathVariable String userId) {
        List<FollowResponse> list = followRelationService.getFollowing(userId);
        return ResponseEntity.ok(new ApiResponse<>("Fetched following successfully", list));
    }
}
