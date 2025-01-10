package com.demo.rest.laboratorul11.repository;

import com.demo.rest.laboratorul11.config.PasswordUtil;
import com.demo.rest.laboratorul11.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class UserRepository extends DataRepository<User, Long>{

    @PersistenceContext
    private EntityManager entityManager;

    public UserRepository() {
        super(User.class);
    }

    public User login(String username, String password) {
        String hashedPassword = PasswordUtil.hashPassword(password);


        TypedQuery<User> query = entityManager.createNamedQuery("User.login", User.class);
        query.setParameter("username", username);
        query.setParameter("password", hashedPassword);

        try {
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null; // Handle no result scenario
        }
    }
}
