package com.example.lab7.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

@RequestScoped
public class EvaluationNotificationObserver {


    public void onEvaluationSubmitted(@Observes EvaluationSubmittedEvent event) {
        String message = String.format("New evaluation submitted for %s in the course %s by teacher %s. Grade: %d, Comment: %s",
                event.getStudentUsername(), event.getCourseName(), event.getTeacherName(), event.getGrade(), event.getComment());

        System.out.println("Observer received event: " + message);
    }

}
