package com.example.laboratorul8.model;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@NamedQuery(
        name = "Course.findByName",
        query = "SELECT c FROM Course c WHERE c.name = :name"
)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int year;
    private int semester;
    private String language;
    private boolean isCompulsory;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "teacher_course",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    @JsonbTransient
    private Set<Teacher> teachers;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", teachers=" + (teachers != null ? teachers.size() : 0) +
                '}';
    }
}
