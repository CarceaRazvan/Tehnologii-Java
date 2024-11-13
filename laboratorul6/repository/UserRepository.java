package com.example.lab6.repository;

import com.example.lab6.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;

@Stateless
public class UserRepository extends DataRepository<User, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepository() {
        super(User.class);
    }

    protected boolean isNew(User user) {
        return user.getId() == null;
    }

    public User findByEmail(String email) {
        try {
            return entityManager.createNamedQuery("User.findByEmail", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
