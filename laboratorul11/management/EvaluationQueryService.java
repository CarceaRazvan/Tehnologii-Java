package com.demo.rest.laboratorul11.management;

import com.demo.rest.laboratorul11.model.Evaluation;
import com.demo.rest.laboratorul11.model.EventLog;
import com.demo.rest.laboratorul11.repository.EventRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class EvaluationQueryService {

    @Inject
    private EventRepository eventRepository;

    public Evaluation getEvaluation(String evaluationId) {
        // Retrieve the events for the specific evaluation
        List<EventLog> events = eventRepository.findEventsForEntity(evaluationId);

        Evaluation evaluation = new Evaluation();
        for (EventLog event : events) {
            evaluation.applyEvent(event);
        }
        return evaluation;
    }
}