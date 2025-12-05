package com.example.userservice.mapper;

import com.example.commonlib.enums.ConcernTypeEnum;
import com.example.commonlib.enums.SkinTypeEnum;
import com.example.userservice.dto.user_preference.*;
import com.example.userservice.model.UserPreference;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserPreferenceMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferencesText", ignore = true)
    @Mapping(target = "skinType", expression = "java(enumToString(request.getSkinType()))")
    @Mapping(target = "concerns", expression = "java(enumListToStringList(request.getConcerns()))")
    UserPreference toModel(UserPreferenceCreationRequest request);

    @Mapping(target = "userId", expression = "java(userPreference.getUser().getId())")
    @Mapping(target = "skinType", expression = "java(stringToSkinTypeEnum(userPreference.getSkinType()))")
    @Mapping(target = "concerns", expression = "java(stringListToEnumList(userPreference.getConcerns()))")
    UserPreferenceResponse toResponse(UserPreference userPreference);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "preferencesText", ignore = true)
    @Mapping(target = "skinType", expression = "java(enumToString(request.getSkinType()))")
    @Mapping(target = "concerns", expression = "java(enumListToStringList(request.getConcerns()))")
    void updateFromRequest(UserPreferenceUpdateRequest request, @MappingTarget UserPreference preference);

    default String enumToString(SkinTypeEnum skinType) {
        return skinType != null ? skinType.name() : null;
    }

    default SkinTypeEnum stringToSkinTypeEnum(String skinType) {
        if (skinType == null) return null;
        try {
            return SkinTypeEnum.valueOf(skinType);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    default List<String> enumListToStringList(List<ConcernTypeEnum> concerns) {
        if (concerns == null) return null;
        return concerns.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    default List<ConcernTypeEnum> stringListToEnumList(List<String> concerns) {
        if (concerns == null) return null;
        return concerns.stream()
                .map(s -> {
                    try {
                        return ConcernTypeEnum.valueOf(s);
                    } catch (IllegalArgumentException e) {
                        return null;
                    }
                })
                .filter(c -> c != null)
                .collect(Collectors.toList());
    }
}
