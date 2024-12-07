package com.example.laboratorul8.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named
@ApplicationScoped
public class EvaluationConfig {

    @Getter
    private final LocalDateTime startDate = LocalDateTime.now();
    @Getter
    private final LocalDateTime endDate = LocalDateTime.now().plusMinutes(1);

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return startDate.format(formatter);
    }

    public String getFormattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return endDate.format(formatter);
    }

}