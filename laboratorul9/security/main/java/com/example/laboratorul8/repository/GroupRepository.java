package com.example.laboratorul8.repository;

import com.example.laboratorul8.model.Group;
import com.example.laboratorul8.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class GroupRepository extends DataRepository<Group, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public GroupRepository() {
        super(Group.class);
    }

}
