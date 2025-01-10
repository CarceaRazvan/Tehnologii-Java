package com.demo.rest.laboratorul11.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Table(name = "event_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Getter
    @Setter
    @Lob
    @Column(name = "payload", nullable = false)
    private String payload; // Store as a String (instead of jsonb)

    @Getter
    @Setter
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    private String username; // New column for username
}