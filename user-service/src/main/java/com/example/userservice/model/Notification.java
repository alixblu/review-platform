package com.example.userservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Node("Notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(generatorClass = UUIDStringGenerator.class)
    private String id;

    @Property("user_id")
    private UUID userId;

    @Property("type")
    private NotiType type;

    @Property("description")
    private String description;

    @Property("create_at")
    private LocalDateTime createAt;

    @Property("is_read")
    private boolean isRead;

    @Property("endpoint")
    private String endpoint;

    public enum NotiType {
        INFO,
        WARNING,
        ERROR,
        SUCCESS
    }
}
