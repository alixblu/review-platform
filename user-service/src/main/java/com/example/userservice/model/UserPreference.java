package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.Instant;
import java.util.List;

@Node("UserPreference")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPreference {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Relationship(type = "HAS_PREFERENCE", direction = Relationship.Direction.INCOMING)
    private User user;

    @Property("skin_type")
    private String skinType;  // từ SkinTypeEnum

    @Property("concerns")
    private List<String> concerns;  // từ ConcernTypeEnum

    @Property("preferences_text")
    private String preferencesText;

    @Property("embedding")
    private String embedding;

    @Property("updated_at")
    private Instant updatedAt;
}
