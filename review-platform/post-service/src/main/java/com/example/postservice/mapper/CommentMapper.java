package com.example.postservice.mapper;

import com.example.postservice.dto.comment.CommentCreationRequest;
import com.example.postservice.dto.comment.CommentResponse;
import com.example.postservice.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    // Không cần map postId, vì service sẽ set đối tượng Post
    Comment toModel(CommentCreationRequest request);

    // Tự động map comment.post.id thành postId
    @Mapping(source = "post.id", target = "postId")
    CommentResponse toResponse(Comment comment);
}