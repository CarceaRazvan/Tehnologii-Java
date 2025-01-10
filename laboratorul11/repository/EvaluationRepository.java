package com.demo.rest.laboratorul11.repository;

import com.demo.rest.laboratorul11.model.Evaluation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EvaluationRepository extends DataRepository<Evaluation, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public EvaluationRepository() {
        super(Evaluation.class);
    }

    public Evaluation findByUsernameAndCourseAndActivity(String username, String course, String activity) {
        try {
            // Using JPQL to query based on username, course, and activity
            return entityManager.createQuery(
                            "SELECT e FROM Evaluation e WHERE e.username = :username AND e.course = :course AND e.activity = :activity",
                            Evaluation.class)
                    .setParameter("username", username)
                    .setParameter("course", course)
                    .setParameter("activity", activity)
                    .getSingleResult();
        } catch (Exception e) {
            // Log error or handle exception as needed (e.g., return null or throw exception)
            return null; // Or handle the exception as per your requirements
        }
    }
}