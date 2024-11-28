package com.example.lab7.repository;

import com.example.lab7.model.Teacher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TeacherRepository extends DataRepository<Teacher, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public TeacherRepository() {
        super(Teacher.class);
    }

    public Teacher findByName(String teacherName) {
        return entityManager.createNamedQuery("Teacher.findByName", Teacher.class)
                .setParameter("name", teacherName)
                .getSingleResult();
    }

    public Teacher findByUsername(String teacherUsername) {
        return entityManager.createNamedQuery("Teacher.findByUsername", Teacher.class)
                .setParameter("username", teacherUsername)
                .getSingleResult();
    }

}
