package com.example.laboratorul8.service;

import com.example.laboratorul8.model.Course;
import com.example.laboratorul8.model.Teacher;
import com.example.laboratorul8.repository.TeacherRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class TeacherService {

    @Inject
    TeacherRepository teacherRepository;

    public List<String> findAllTeachers() {

        List<Teacher> allTeachers = teacherRepository.findAll();

        return allTeachers.stream()
                .map(Teacher::getName)
                .collect(Collectors.toList());
    }

    public Teacher findByName(String teacherName) {

        return teacherRepository.findByName(teacherName);
    }

    public Teacher findByUsername(String teacherUsername) {

        return teacherRepository.findByUsername(teacherUsername);
    }


    public List<String> getTeacherCourses(String teacherName) {

        Teacher teacher = findByName(teacherName);

        Set<Course> teacherCourses = teacher.getCourses();

        return teacherCourses.stream()
                .map(Course::getName)
                .collect(Collectors.toList());
    }
}
