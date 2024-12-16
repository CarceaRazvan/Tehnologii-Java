package com.example.laboratorul8.service;

import com.example.laboratorul8.model.Student;
import com.example.laboratorul8.repository.StudentRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class StudentService {

    @Inject
    StudentRepository studentRepository;

    public Student findByUsername(String studentUsername) {

        return studentRepository.findByUsername(studentUsername);
    }
}
