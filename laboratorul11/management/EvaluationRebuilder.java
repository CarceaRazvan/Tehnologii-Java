package com.demo.rest.laboratorul11.management;

import com.demo.rest.laboratorul11.model.Evaluation;
import com.demo.rest.laboratorul11.model.EventLog;
import com.demo.rest.laboratorul11.repository.EventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;

@RequestScoped
public class EvaluationRebuilder {

    @Inject
    private EventRepository eventRepository;

    public Evaluation reconstructEvaluation(String username, String course, String activity) {
        List<EventLog> events = eventRepository.findByUsernameAndCourseAndActivity(username, course, activity);

        Evaluation evaluation = new Evaluation();
        for (EventLog event : events) {
            switch (event.getEventType()) {
                case "EvaluationSubmitted":
                    applyEvaluationSubmitted(evaluation, event);
                    break;
                default:
                    throw new IllegalStateException("Unknown event type: " + event.getEventType());
            }
        }

        return evaluation;
    }

    private void applyEvaluationSubmitted(Evaluation evaluation, EventLog event) {
        JsonNode payload = null;
        try {
            payload = new ObjectMapper().readTree(event.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        evaluation.setUsername(payload.get("username").asText());
        evaluation.setCourse(payload.get("course").asText());
        evaluation.setActivity(payload.get("activity").asText());
        evaluation.setGrade(payload.get("grade").asInt());
        evaluation.setComment(payload.get("comment").asText());
    }

}