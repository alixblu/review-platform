package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.util.UUID;

@Node("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id; // Neo4j internal UUID

    // Business user ID (unique external reference)
    @Property("user_id")
    private UUID userId;

    @Property("name")
    private String name;

    @Property("age")
    private Integer age;

    @Property("gender")
    private Gender gender;

    @Property("profile_pic")
    private String profilePic;

    public enum Gender {
        MALE,
        FEMALE
    }
}
