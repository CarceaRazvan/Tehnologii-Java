package com.demo.rest.laboratorul11.repository;

import com.demo.rest.laboratorul11.model.Evaluation;
import com.demo.rest.laboratorul11.model.EventLog;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class EventRepository extends DataRepository<EventLog, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public EventRepository() {
        super(EventLog.class);
    }

    public List<EventLog> findEventsForEntity(String entityId) {
        // Retrieve all events for a specific entity (e.g., evaluation)
        return entityManager.createQuery("SELECT e FROM EventLog e WHERE e.payload LIKE :entityId", EventLog.class)
                .setParameter("entityId", "%" + entityId + "%")
                .getResultList();
    }

    public List<EventLog> findByUsernameAndCourseAndActivity(String username, String course, String activity) {
        return entityManager.createQuery(
                        "SELECT e FROM EventLog e WHERE " +
                                "e.payload LIKE :username AND " +
                                "e.payload LIKE :course AND " +
                                "e.payload LIKE :activity", EventLog.class)
                .setParameter("username", "%\"username\":\"" + username + "\"%")
                .setParameter("course", "%\"course\":\"" + course + "\"%")
                .setParameter("activity", "%\"activity\":\"" + activity + "\"%")
                .getResultList();
    }
}
