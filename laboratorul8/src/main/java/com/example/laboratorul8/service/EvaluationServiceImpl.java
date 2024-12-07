package com.example.laboratorul8.service;

import com.example.laboratorul8.interceptor.LogExecutionTimeInterceptor;
import com.example.laboratorul8.model.*;
import com.example.laboratorul8.observer.EvaluationSubmittedEvent;
import com.example.laboratorul8.produces.RegistrationNumberQualifier;
import com.example.laboratorul8.repository.EvaluationRepository;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Interceptors(LogExecutionTimeInterceptor.class)
@RequestScoped
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    StudentService studentService;

    @Inject
    TeacherService teacherService;

    @Inject
    private CourseService courseService;

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    @RegistrationNumberQualifier
    private String registrationNumber;

    @Inject
    private Event<EvaluationSubmittedEvent> evaluationSubmittedEvent;

    private static final Random RANDOM = new Random();


    @Transactional
    public void update(Evaluation evaluation) {

        evaluationRepository.update(evaluation);
    }

    @Transactional
    public boolean delete(Long id) {
        Evaluation evaluation = evaluationRepository.findById(id);

        if (evaluation != null) {
            evaluationRepository.remove(evaluation);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public boolean sendEvaluation(String studentUsername, String teacherName, String courseName, String activityName, int grade, String comment) {

        try {
            Student student = studentService.findByUsername(studentUsername);
            Teacher teacher = teacherService.findByName(teacherName);
            Course course = courseService.findByName(courseName);

            Evaluation evaluation = new Evaluation();
            evaluation.setStudent(student);
            evaluation.setTeacher(teacher);
            evaluation.setCourse(course);
            evaluation.setActivity(activityName);
            evaluation.setGrade(grade);
            evaluation.setComment(comment);
            evaluation.setRegistrationNumber(registrationNumber);

            evaluationRepository.persist(evaluation);

            EvaluationSubmittedEvent event = new EvaluationSubmittedEvent(studentUsername, teacherName, courseName, activityName, grade, comment);
            evaluationSubmittedEvent.fire(event);

            return true;

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    @Transactional
    public List<Evaluation> getEvaluations(String teacherUsername, String role) {

        if (Objects.equals(role, "TEACHER")) {

            Teacher teacher = teacherService.findByUsername(teacherUsername);

            System.out.println("teacher:" + teacherUsername);

            return evaluationRepository.findAllByTeacher(teacher);

        }

        if (Objects.equals(role, "ADMIN")) {
            return evaluationRepository.findAll();
        }


        return null;
    }

    public Evaluation getEvaluationById(Long id) {

        return evaluationRepository.findById(id);
    }

    public List<TeacherStatistics> getTeacherStatistics() {

        return evaluationRepository.getTeacherStatistics();
    }


    @Transactional
    public Evaluation generateRandomEvaluation(String sentiment, String teacherUsername, String courseName) {

        Teacher teacher = teacherService.findByUsername(teacherUsername);
        Course course = courseService.findByName(courseName);

        if (teacher == null || course == null)
            return null;

        Random random = new Random();

        int grade;
        switch (sentiment.toLowerCase()) {
            case "negative":
                grade = random.nextInt(4) + 1; // Grades 1-4
                break;
            case "neutral":
                grade = random.nextInt(3) + 5; // Grades 5-7
                break;
            case "positive":
                grade = random.nextInt(3) + 8; // Grades 8-10
                break;
            default:
                throw new IllegalArgumentException("Invalid sentiment. Allowed values: negative, neutral, positive.");
        }

        String comment = generateComment(sentiment);

        Student student = studentService.findByUsername("computer");
        String activityName = ThreadLocalRandom.current().nextBoolean() ? "Course" : "Laboratory";


        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(student);
        evaluation.setTeacher(teacher);
        evaluation.setCourse(course);
        evaluation.setActivity(activityName);
        evaluation.setGrade(grade);
        evaluation.setComment(comment);
        evaluation.setRegistrationNumber(registrationNumber);

        evaluationRepository.persist(evaluation);

        return evaluation;

    }

    public String analyzeTeacherSentiment(String teacherUsername) {
        Teacher teacher = teacherService.findByUsername(teacherUsername);

        if (teacher == null) {
            throw new IllegalArgumentException("Teacher not found with the provided username.");
        }

        List<Evaluation> evaluations = evaluationRepository.findAllByTeacher(teacher);

        if (evaluations.isEmpty()) {
            return "No evaluations found for this teacher.";
        }

        StringBuilder comments = new StringBuilder();
        for (Evaluation evaluation : evaluations) {
            if (evaluation.getComment() != null && !evaluation.getComment().isEmpty()) {
                comments.append(evaluation.getComment()).append(". ");
            }
        }

        if (comments.length() == 0) {
            return "No comments available to analyze.";
        }

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,parse,sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String text = comments.toString();
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        int positive = 0, neutral = 0, negative = 0;

        for (CoreMap sentence : document.get(CoreAnnotations.SentencesAnnotation.class)) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);

            switch (sentiment.toLowerCase()) {
                case "positive":
                    positive++;
                    break;
                case "neutral":
                    neutral++;
                    break;
                case "negative":
                    negative++;
                    break;
            }
        }

        int total = positive + neutral + negative;
        return String.format(
                "Sentiment Analysis for %s: Positive: %d%%, Neutral: %d%%, Negative: %d%%",
                teacher.getName(),
                (positive * 100 / total),
                (neutral * 100 / total),
                (negative * 100 / total)
        );
    }

    public static String generateComment(String sentiment) {
        // Define possible comments for each sentiment
        List<String> negativeComments = List.of(
                "Needs significant improvement.",
                "Consider revising your approach to teaching.",
                "Performance is below expectations and needs attention.",
                "Students might be struggling to connect with the material."
        );

        List<String> neutralComments = List.of(
                "Satisfactory performance.",
                "A balanced approach, but there’s room for improvement.",
                "Consistent but not exceptional.",
                "Meeting expectations but could aim for more engagement."
        );

        List<String> positiveComments = List.of(
                "Outstanding work and teaching!",
                "A fantastic job engaging and inspiring students.",
                "Teaching methods are highly effective and commendable.",
                "An excellent performance that sets a great example."
        );

        // Select a comment based on sentiment
        switch (sentiment.toLowerCase()) {
            case "negative":
                return negativeComments.get(RANDOM.nextInt(negativeComments.size()));
            case "neutral":
                return neutralComments.get(RANDOM.nextInt(neutralComments.size()));
            case "positive":
                return positiveComments.get(RANDOM.nextInt(positiveComments.size()));
            default:
                return "No comment available for this sentiment.";
        }
    }

}
