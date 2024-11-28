package com.example.lab7.service;


public interface EvaluationService {

    boolean sendEvaluation(String studentUsername, String teacherName, String courseName, String activityName, int grade, String comment);
}