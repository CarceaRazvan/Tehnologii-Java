package com.example.lab7.repository;

import com.example.lab7.model.Course;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@ApplicationScoped
public class CourseRepository extends DataRepository<Course, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public CourseRepository() {
        super(Course.class);
    }

    public Course findByName(String name) {
        return entityManager.createNamedQuery("Course.findByName", Course.class)
                .setParameter("name", name)
                .getSingleResult();
    }

}
