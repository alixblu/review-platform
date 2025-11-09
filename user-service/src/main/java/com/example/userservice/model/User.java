package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
import org.springframework.lang.Nullable;

import java.util.UUID;

@Node("User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;  // use Neo4j’s internal UUID generator


    // External business UUID (unique)
    @Property("acc_id")
    private UUID accId;

    // Name
    @Property("name")
    private String name;


    // Optional profile picture
    @Property("profile_pic")
//    @Nullable
    private String profilePic;

//    // Enum gender
//    @Property("gender")
//    private Gender gender;
//    // Gender enum
//    public enum Gender {
//        MALE,
//        FEMALE
//    }
}
