package com.example.laboratorul8.edit;


import com.example.laboratorul8.service.AuthService;
import com.example.laboratorul8.service.CourseService;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Named
@SessionScoped
public class RegisterBean implements Serializable {

    @Getter
    private final List<String> availableRoles = Arrays.asList("TEACHER", "STUDENT");
    @Getter
    private List<String> allCourses;

    @Getter
    @Setter
    private List<String> coursesSelected;

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String userRole;

    @Getter
    @Setter
    private int year;

    @Getter
    @Setter
    private int semester;

    @Inject
    AuthService authService;
    @Inject
    CourseService courseService;

    @PostConstruct
    public void init() {

        coursesSelected = new ArrayList<>();
        allCourses = courseService.findAllCourses();
    }

    @Transactional
    public void register() {

        boolean success;

        try {
            success = authService.register(name, username, password, userRole, year, semester, coursesSelected);
        } catch (ConstraintViolationException e) {

            Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

            for (ConstraintViolation<?> violation : violations) {
                String propertyPath = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();

                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Validation Error on " + propertyPath + ": " + errorMessage, null);
                FacesContext.getCurrentInstance().addMessage(null, facesMessage);
            }
            success = false;
        }


        System.out.println("succes: " + success);
        System.out.println(username + "---" + password);

        if (success) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Register success!", "Welcome, " + username));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Register error", "Incorrect data."));
        }
    }
}
