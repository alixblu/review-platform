package com.example.userservice.mapper;

import com.example.userservice.dto.follow_relation.FollowRequest;
import com.example.userservice.dto.follow_relation.FollowResponse;
import com.example.userservice.model.FollowRelation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = { java.time.LocalDateTime.class })
public interface FollowRelationMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    FollowRelation toModel(FollowRequest request);

    FollowResponse toResponse(FollowRelation followRelation);
}
