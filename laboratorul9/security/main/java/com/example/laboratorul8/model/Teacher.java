package com.example.laboratorul8.model;


import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Teacher.findByName",
                query = "SELECT t FROM Teacher t WHERE t.name = :name"
        ),
        @NamedQuery(
                name = "Teacher.findByUsername",
                query = "SELECT t FROM Teacher t WHERE t.username = :username"
        )
})
public class Teacher extends User{

    @ManyToMany(mappedBy = "teachers", fetch = FetchType.EAGER)
    @JsonbTransient
    private Set<Course> courses;

    public Teacher() { super(); }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", courses=" + (courses != null ? courses.size() : 0) +
                '}';
    }
}
