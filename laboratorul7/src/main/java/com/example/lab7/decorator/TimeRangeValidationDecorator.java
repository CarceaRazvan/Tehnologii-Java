package com.example.lab7.decorator;

import com.example.lab7.config.EvaluationConfig;
import com.example.lab7.service.EvaluationService;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;

import java.time.LocalDateTime;

@Decorator
public abstract class TimeRangeValidationDecorator implements EvaluationService {

    @Inject
    @Delegate
    @Any
    private EvaluationService EvaluationService;

    @Inject
    private EvaluationConfig evaluationConfig;

    public boolean sendEvaluation(String studentUsername, String teacherName, String courseName, String activityName, int grade, String comment) {
        LocalDateTime currentTime = LocalDateTime.now();

        System.out.println("DECORATOR");

        if (currentTime.isBefore(evaluationConfig.getStartDate()) || currentTime.isAfter(evaluationConfig.getEndDate())) {
            System.out.println("Error: Evaluation cannot be submitted outside the allowed time range.");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Evaluation cannot be submitted outside the allowed time range.", null));
            return false;
        }

        return EvaluationService.sendEvaluation(studentUsername, teacherName, courseName, activityName, grade, comment);
    }
}