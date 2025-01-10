package com.demo.rest.laboratorul11.management;

import com.demo.rest.laboratorul11.service.EvaluationService;
import com.demo.rest.laboratorul11.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class SagaOrchestrator {

    @Inject
    private EvaluationService evaluationService;

    @Inject
    private UserService userService;

    @Transactional
    public boolean submitEvaluation(String username, String teacher, String course, String activity, int grade, String comment) {
        try {
            // Step 1: Submit evaluation
            boolean evaluationSuccess = evaluationService.sendEvaluation(username, teacher, course, activity, grade, comment);

            if (!evaluationSuccess) {
                throw new RuntimeException("Evaluation submission failed");
            }

            // Step 2: Increment user counter
            boolean userCounterUpdated = userService.incrementCounter(username);

            if (!userCounterUpdated) {
                // If user counter update fails, perform compensating actions
                evaluationService.rollbackEvaluation(username, course, activity);
                throw new RuntimeException("User counter update failed");
            }

            return true;

        } catch (Exception e) {
            System.out.println("Saga failed: " + e.getMessage());
            return false;
        }
    }

}
