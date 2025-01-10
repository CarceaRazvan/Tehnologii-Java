package com.demo.rest.laboratorul11.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String teacher;
    private String course;

    private String activity;

    private int grade;
    private String comment;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void applyEvent(EventLog event) {
        try {
            JsonNode payload = objectMapper.readTree(event.getPayload());

            // Apply event details to the Evaluation
            this.username = payload.get("username").asText();
            this.course = payload.get("course").asText();
            this.activity = payload.get("activity").asText();
            this.grade = payload.get("grade").asInt();
            this.comment = payload.get("comment").asText();
            this.teacher = payload.get("teacher").asText();

        } catch (Exception e) {
            throw new RuntimeException("Error applying event", e);
        }
    }

}
