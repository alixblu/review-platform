package com.example.postservice.mapper;

import com.example.postservice.dto.post.PostCreationRequest;
import com.example.postservice.dto.post.PostResponse;
import com.example.postservice.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

    PostResponse toResponse(Post post);

    Post toModel(PostCreationRequest postCreationRequest);
}
