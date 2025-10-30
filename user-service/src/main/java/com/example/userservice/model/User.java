//package com.example.userservice.model;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.neo4j.core.schema.*;
//import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
//import org.springframework.lang.Nullable;
//
//import java.util.UUID;
//
//@Node("User")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class User {
//
//    // Internal Neo4j ID (auto-generated)
//    @Id
//    @GeneratedValue (generatorClass = UUIDStringGenerator.class)
//    private Long id;
//
//    // External business UUID (unique)
//    @Property("acc_id")
//    @Index(unique = true)
//    private UUID accId;
//
//    // Name
//    @Property("name")
//    private String name;
//
//    // Age (>16)
//    @Property("age")
//    private Integer age;
//
//    // Enum gender
//    @Property("gender")
//    private Gender gender;
//
//    // Optional profile picture
//    @Property("profile_pic")
//    @Nullable
//    private String profilePic;
//
//    // Gender enum
//    public enum Gender {
//        MALE,
//        FEMALE,
//        OTHER
//    }
//}
