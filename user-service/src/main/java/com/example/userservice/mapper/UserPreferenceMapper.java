package com.example.userservice.mapper;

import com.example.userservice.dto.user_preference.*;
import com.example.userservice.model.UserPreference;
import com.example.userservice.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserPreferenceMapper {

    @Mapping(target = "user", ignore = true) // set riêng trong service
    @Mapping(target = "updatedAt", ignore = true)
    UserPreference toModel(UserPreferenceCreationRequest request);

    @Mapping(target = "userId", expression = "java(userPreference.getUser().getUserId())")
    UserPreferenceResponse toResponse(UserPreference userPreference);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateFromRequest(UserPreferenceUpdateRequest request, @MappingTarget UserPreference preference);
}
