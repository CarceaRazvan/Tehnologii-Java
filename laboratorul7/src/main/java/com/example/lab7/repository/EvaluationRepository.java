package com.example.lab7.repository;

import com.example.lab7.model.Evaluation;
import com.example.lab7.model.Teacher;
import com.example.lab7.model.TeacherStatistics;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class EvaluationRepository extends DataRepository<Evaluation, Long> {

    @PersistenceContext
    private EntityManager entityManager;

    public EvaluationRepository() {
        super(Evaluation.class);
    }

    public List<Evaluation> findAllByTeacher(Teacher teacher) {
        return entityManager.createNamedQuery("Evaluation.findByTeacher", Evaluation.class)
                .setParameter("teacher", teacher)
                .getResultList();
    }

    public List<TeacherStatistics> getTeacherStatistics() {
        List<TeacherStatistics> statistics = new ArrayList<>();

        TypedQuery<Object[]> result = entityManager.createNamedQuery("Evaluation.getTeacherStatistics", Object[].class);
        List<Object[]> resultList = result.getResultList();

        for (Object[] resultItem : resultList) {
            String teacherName = (String) resultItem[0];
            Double avgGrade = (Double) resultItem[1];
            Long submissionCount = (Long) resultItem[2];

            TeacherStatistics stats = new TeacherStatistics(teacherName, avgGrade, submissionCount);
            statistics.add(stats);
        }

        return statistics;
    }
}
