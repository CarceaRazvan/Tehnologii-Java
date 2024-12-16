package com.example.laboratorul8.edit;

import com.example.laboratorul8.service.EvaluationServiceImpl;
import com.example.laboratorul8.service.TeacherService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@SessionScoped
public class EvaluateBean implements Serializable {

    @Getter
    private List<String> allTeachersNames;
    private List<String> teacherCourses;
    @Getter
    private final List<String> activities = Arrays.asList("Course", "Laboratory");

    @Getter
    @Setter
    private String studentUsername;

    @Getter
    @Setter
    private String teacherName;
    @Getter
    @Setter
    private String courseName;
    @Getter
    @Setter
    private String activityName;
    @Getter
    @Setter
    private int grade;
    @Getter
    @Setter
    private String comment;

    @Inject
    TeacherService teacherService;

    @Inject
    EvaluationServiceImpl evaluationServiceImpl;

    @PostConstruct
    public void init() {

        allTeachersNames = teacherService.findAllTeachers();
        grade = 1;
    }

    public List<String> getTeacherCourses() {

        if (teacherName != null) {

            teacherCourses = teacherService.getTeacherCourses(teacherName);
            return teacherCourses;
        }

        return null;

    }

    @Transactional
    public void evaluate() {

        boolean success;

        try {
            success = evaluationServiceImpl.sendEvaluation(studentUsername, teacherName, courseName, activityName, grade, comment);

            System.out.println("Success"+ success);
            System.out.println("Student Name: " + studentUsername);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            success = false;
        }

        if (success) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Evaluation submitted successfully!", null));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error submitting evaluation!", null));
        }
    }

}
