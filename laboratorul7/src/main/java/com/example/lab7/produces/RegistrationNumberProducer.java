package com.example.lab7.produces;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

import java.util.UUID;

@ApplicationScoped
public class RegistrationNumberProducer {

    @Produces
    @RegistrationNumberQualifier
    public String generateRegistrationNumber() {
        return UUID.randomUUID().toString();
    }
}