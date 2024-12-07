package com.example.laboratorul8.repository;

import com.example.laboratorul8.model.Student;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class StudentRepository extends DataRepository<Student, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public StudentRepository() {
        super(Student.class);
    }

    public Student findByUsername(String studentUsername) {
        return entityManager.createNamedQuery("Student.findByUsername", Student.class)
                .setParameter("username", studentUsername)
                .getSingleResult();
    }
}