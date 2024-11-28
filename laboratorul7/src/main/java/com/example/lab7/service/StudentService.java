package com.example.lab7.service;

import com.example.lab7.model.Student;
import com.example.lab7.repository.StudentRepository;
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
