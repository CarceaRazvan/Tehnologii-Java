package com.example.lab7.service;

import com.example.lab7.model.*;
import com.example.lab7.observer.EvaluationSubmittedEvent;
import com.example.lab7.interceptor.LogExecutionTimeInterceptor;
import com.example.lab7.produces.RegistrationNumberQualifier;
import com.example.lab7.repository.EvaluationRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;

import java.util.List;
import java.util.Objects;

@Interceptors(LogExecutionTimeInterceptor.class)
@RequestScoped
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    StudentService studentService;

    @Inject
    TeacherService teacherService;

    @Inject
    private CourseService courseService;

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    @RegistrationNumberQualifier
    private String registrationNumber;

    @Inject
    private Event<EvaluationSubmittedEvent> evaluationSubmittedEvent;

    public boolean sendEvaluation(String studentUsername, String teacherName, String courseName, String activityName, int grade, String comment) {

        try {
            Student student = studentService.findByUsername(studentUsername);
            Teacher teacher = teacherService.findByName(teacherName);
            Course course = courseService.findByName(courseName);

            Evaluation evaluation = new Evaluation();
            evaluation.setStudent(student);
            evaluation.setTeacher(teacher);
            evaluation.setCourse(course);
            evaluation.setActivity(activityName);
            evaluation.setGrade(grade);
            evaluation.setComment(comment);
            evaluation.setRegistrationNumber(registrationNumber);

            evaluationRepository.persist(evaluation);

            EvaluationSubmittedEvent event = new EvaluationSubmittedEvent(studentUsername, teacherName, courseName, activityName, grade, comment);
            evaluationSubmittedEvent.fire(event);

            return true;

        }  catch (IllegalArgumentException e) {
            System.out.println("Error: "+e.getMessage());
            return false;
        }
    }

    public List<Evaluation> getEvaluations(String teacherUsername, String role) {

        if (Objects.equals(role, "TEACHER")) {

            Teacher teacher = teacherService.findByUsername(teacherUsername);

            System.out.println("teacher:" + teacherUsername);

            return evaluationRepository.findAllByTeacher(teacher);

        }

        if (Objects.equals(role, "ADMIN")) {
            return evaluationRepository.findAll();
        }


        return null;
    }

    public Evaluation getEvaluationById(Long id) {

        return evaluationRepository.findById(id);
    }

    public List<TeacherStatistics> getTeacherStatistics() {

        return evaluationRepository.getTeacherStatistics();
    }
}
