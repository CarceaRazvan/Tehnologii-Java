package com.demo.rest.laboratorul11.service;

import com.demo.rest.laboratorul11.config.PasswordUtil;
import com.demo.rest.laboratorul11.model.EventLog;
import com.demo.rest.laboratorul11.model.User;
import com.demo.rest.laboratorul11.repository.EventRepository;
import com.demo.rest.laboratorul11.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequestScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    private EventRepository eventRepository;

    @Transactional
    public boolean register(String username, String password) {

        try {

            User user = new User();
            user.setUsername(username);
            String hashedPassword = PasswordUtil.hashPassword(password);
            user.setPassword(hashedPassword);

            userRepository.persist(user);

            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());

            return false;
        }
    }

    public User login(String username, String password) throws ServletException {

        try {

            User user = userRepository.login(username, password);
            if (user == null) {
                throw new RuntimeException("Username sau parolă incorectă.");
            }
            return user;

        } catch (RuntimeException e) {
            throw new RuntimeException("Eroare aplicație: " + e.getMessage(), e);
        }

    }

    public User getByUsername(String username) {

        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("User not found: " + username);
            return null;
        }

        return user;
    }

    @Transactional
    public boolean incrementCounter(String username) {
        try {
            // Fetch user
            User user = userRepository.findByUsername(username);

            if (user == null) {
                System.out.println("User not found: " + username);
                return false;
            }

            user.setCounter(user.getCounter() + 1);
            userRepository.persist(user);



            // Publish the event
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode payload = objectMapper.createObjectNode()
                    .put("from", user.getCounter() - 1)
                    .put("to", user.getCounter());

            String serializedPayload = payload.toString();

            EventLog eventLogIncrement = new EventLog();
            eventLogIncrement.setEventType("Increment counter");
            eventLogIncrement.setPayload(serializedPayload);
            eventLogIncrement.setTimestamp(LocalDateTime.now());
            eventLogIncrement.setUsername(username);

            eventRepository.persist(eventLogIncrement);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
