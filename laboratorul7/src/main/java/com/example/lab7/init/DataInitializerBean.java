package com.example.lab7.init;

import com.example.lab7.model.*;
import com.example.lab7.produces.RegistrationNumberQualifier;
import com.example.lab7.repository.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.inject.Singleton;

import java.util.HashSet;
import java.util.Set;



@Singleton
public class DataInitializerBean {

    @Inject
    private TeacherRepository teacherRepository;

    @Inject
    private CourseRepository courseRepository;
    @Inject
    private StudentRepository studentRepository;
    @Inject
    private UserRepository userRepository;
    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    @RegistrationNumberQualifier
    private String registrationNumber;

    @Transactional
    public void initData() {
        User adminUser = new User();
        adminUser.setName("Admin");
        adminUser.setUsername("admin");
        adminUser.setPassword("123");
        adminUser.setUserRole(UserRole.ADMIN);

        Teacher teacher1 = new Teacher();
        teacher1.setName("Bogdan Patrut");
        teacher1.setUsername("bogdan_patrut");
        teacher1.setPassword("123");
        teacher1.setUserRole(UserRole.TEACHER);

        Teacher teacher2 = new Teacher();
        teacher2.setName("Ionita Alexandru");
        teacher2.setUsername("ionita_alexandru");
        teacher2.setPassword("123");
        teacher2.setUserRole(UserRole.TEACHER);

        Student student1 = new Student();
        student1.setName("Carcea Razvan");
        student1.setUsername("carcea_razvan");
        student1.setPassword("123");
        student1.setUserRole(UserRole.STUDENT);
        student1.setYear(3);
        student1.setSemester(2);

        userRepository.persist(adminUser);
        teacherRepository.persist(teacher1);
        teacherRepository.persist(teacher2);
        studentRepository.persist(student1);


        Course course1 = courseRepository.newInstance();
        course1.setName("Introducere in programare");
        course1.setYear(1);
        course1.setSemester(1);

        Course course2 = courseRepository.newInstance();
        course2.setName("Sisteme de operare");
        course2.setYear(1);
        course2.setSemester(2);


        createCourse(teacher1, course1);
        createCourse(teacher1, course2);

        createCourse(teacher2, course1);

        Evaluation evaluation = new Evaluation();
        evaluation.setStudent(student1);
        evaluation.setTeacher(teacher1);
        evaluation.setCourse(course1);
        evaluation.setActivity("Course");
        evaluation.setGrade(7);
        evaluation.setComment("Very hard course");
        evaluation.setRegistrationNumber(registrationNumber);
        evaluationRepository.persist(evaluation);

        Evaluation evaluation2 = new Evaluation();
        evaluation2.setStudent(student1);
        evaluation2.setTeacher(teacher1);
        evaluation2.setCourse(course2);
        evaluation2.setActivity("Laboratory");
        evaluation2.setGrade(8);
        evaluation2.setComment("Very hard course again");
        evaluation2.setRegistrationNumber(registrationNumber);
        evaluationRepository.persist(evaluation2);

        Evaluation evaluation3 = new Evaluation();
        evaluation3.setStudent(student1);
        evaluation3.setTeacher(teacher2);
        evaluation3.setCourse(course1);
        evaluation3.setActivity("Laboratory");
        evaluation3.setGrade(9);
        evaluation3.setComment("Very important course");
        evaluation3.setRegistrationNumber(registrationNumber);
        evaluationRepository.persist(evaluation3);

        System.out.println("Datele de test au fost adăugate cu succes.");
    }

    private void createCourse(Teacher teacher, Course course) {
        if (teacher != null && course != null) {

            Set<Teacher> teachersForCourse = course.getTeachers();
            if (teachersForCourse == null) {
                teachersForCourse = new HashSet<>();
            }
            teachersForCourse.add(teacher);
            course.setTeachers(teachersForCourse);

            Set<Course> coursesForTeacher = teacher.getCourses();
            if (coursesForTeacher == null) {
                coursesForTeacher = new HashSet<>();
            }
            coursesForTeacher.add(course);
            teacher.setCourses(coursesForTeacher);

            courseRepository.persist(course);

            teacherRepository.persist(teacher);
        }
    }
}