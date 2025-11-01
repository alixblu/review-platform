package com.example.authservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password; // hashed password

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status =  Status.ACTIVE;

    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.FEMALE;

    public enum AccountRole {
        ADMIN,
        USER
    }

    public enum Status {
        ACTIVE,
        HIDDEN
    }

    public enum Gender {
        MALE,
        FEMALE
    }
}
