package com.example.laboratorul8.view;

import com.example.laboratorul8.model.Evaluation;
import com.example.laboratorul8.model.TeacherStatistics;
import com.example.laboratorul8.service.EvaluationServiceImpl;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class EvaluationView implements Serializable{


    @Inject
    private EvaluationServiceImpl evaluationServiceImpl;

    @Getter
    private String selectedComment;

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String role;

    private List<Evaluation> evaluations;

    private List<TeacherStatistics> teacherStatistics;

    @Transactional
    public List<Evaluation> getEvaluations() {

        System.out.println("Getting evaluations");
        System.out.println("Username= "+username);
        System.out.println("Role= "+role);

        evaluations = evaluationServiceImpl.getEvaluations(username, role);

        System.out.println("Evaluations: = "+evaluations);

        return evaluations;
    }

    public void openDialogComment(Long id) {

        Evaluation evaluation = evaluationServiceImpl.getEvaluationById(id);

        selectedComment = evaluation.getComment();
    }

    @Transactional
    public List<TeacherStatistics> getTeacherStatistics() {

        System.out.println("STATS");

        System.out.println("statss"+teacherStatistics);
        return teacherStatistics = evaluationServiceImpl.getTeacherStatistics();

    }

}
