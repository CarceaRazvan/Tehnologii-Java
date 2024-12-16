package com.example.laboratorul8.repository;

import com.example.laboratorul8.config.PasswordUtil;
import com.example.laboratorul8.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
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

}
