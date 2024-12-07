package com.example.laboratorul8.model;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NamedQuery(
        name = "Student.findByUsername",
        query = "SELECT s FROM Student s WHERE s.username = :username"
)
public class Student extends User{

    @Min(value = 1, message = "Year must be between 1 and 3.")
    @Max(value = 3, message = "Year must be between 1 and 3.")
    private int year;

    // Semester should be between 1 and 2, with a custom error message
    @Min(value = 1, message = "Semester must be between 1 and 2.")
    @Max(value = 2, message = "Semester must be between 1 and 2.")
    private int semester;

    public Student() { super(); }
}
