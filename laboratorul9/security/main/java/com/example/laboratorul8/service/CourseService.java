package com.example.laboratorul8.service;

import com.example.laboratorul8.model.Course;
import com.example.laboratorul8.repository.CourseRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CourseService {

    @Inject
    CourseRepository courseRepository;

    public List<String> findAllCourses() {

        List<Course> allCourses = courseRepository.findAll();

        return allCourses.stream()
                .map(Course::getName)
                .collect(Collectors.toList());
    }

    public Course findByName(String courseName) {

        return courseRepository.findByName(courseName);
    }

}
