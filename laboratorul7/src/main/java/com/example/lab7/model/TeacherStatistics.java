package com.example.lab7.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStatistics {

    private String teacherName;
    private Double averageGrade;
    private Long submissionCount;
}
