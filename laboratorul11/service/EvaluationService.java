package com.demo.rest.laboratorul11.service;

import com.demo.rest.laboratorul11.model.Evaluation;
import com.demo.rest.laboratorul11.model.EventLog;
import com.demo.rest.laboratorul11.repository.EvaluationRepository;
import com.demo.rest.laboratorul11.repository.EventRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequestScoped
public class EvaluationService {

    @Inject
    private EventRepository eventRepository;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Transactional
    public boolean sendEvaluation(String username, String teacherName, String courseName, String activityName, int grade, String comment) {

        try {

            Evaluation evaluation = new Evaluation();
            evaluation.setUsername(username);
            evaluation.setTeacher(teacherName);
            evaluation.setCourse(courseName);
            evaluation.setActivity(activityName);
            evaluation.setGrade(grade);
            evaluation.setComment(comment);

            evaluationRepository.persist(evaluation);

            // Create the payload as JsonNode
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payload = objectMapper.createObjectNode()
                    .put("username", username)
                    .put("teacherName", teacherName)
                    .put("course", courseName)
                    .put("activity", activityName)
                    .put("grade", grade)
                    .put("comment", comment);

            // Serialize the payload to a String
            String serializedPayload = payload.toString();

            // Create the EventLog with the serialized payload
            EventLog event = new EventLog();
            event.setEventType("EvaluationSubmitted");
            event.setPayload(serializedPayload); // Set the string payload
            event.setTimestamp(LocalDateTime.now());
            event.setUsername(username);

            // Publish the event
            eventRepository.persist(event);

            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public boolean rollbackEvaluation(String username, String course, String activity) {
        try {
            // Example: Find and delete the evaluation
            Evaluation evaluation = evaluationRepository.findByUsernameAndCourseAndActivity(username, course, activity);

            if (evaluation != null) {
                // Delete the evaluation from the repository
                evaluationRepository.remove(evaluation);
                return true;
            }

            return false;  // Evaluation not found
        } catch (Exception e) {
            System.out.println("Error while rolling back evaluation: " + e.getMessage());
            return false;
        }
    }
}