package com.demo.rest.laboratorul11.management;

import com.demo.rest.laboratorul11.model.EventLog;
import jakarta.inject.Inject;

public class EvaluationEventListener {

    @Inject
    private EvaluationQueryService evaluationQueryService;

    public void onEvent(EventLog event) {
        // In this example, apply event to the read model (query side)
        if ("EvaluationSubmitted".equals(event.getEventType())) {
            // You can handle the event here to update the read model
            System.out.println("New evaluation event received: " + event.getPayload());
        }
    }
}
