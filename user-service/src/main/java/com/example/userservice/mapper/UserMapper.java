package com.example.userservice.mapper;

import com.example.userservice.dto.user.UserCreationRequest;
import com.example.userservice.dto.user.UserResponse;
import com.example.userservice.dto.user.UserUpdateRequest;
import com.example.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

//    @Mapping(target = "gender", constant = "FEMALE")
    User toModel(UserCreationRequest userCreationRequest);
    UserResponse toResponse(User user);

    void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);

}
