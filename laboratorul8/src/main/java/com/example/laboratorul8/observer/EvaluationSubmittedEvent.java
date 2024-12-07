package com.example.laboratorul8.observer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EvaluationSubmittedEvent {

    private String studentUsername;
    private String teacherName;
    private String courseName;
    private String activityName;
    private int grade;
    private String comment;
}
