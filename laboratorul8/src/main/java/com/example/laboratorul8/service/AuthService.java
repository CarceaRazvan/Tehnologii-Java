package com.example.laboratorul8.service;


import com.example.laboratorul8.interceptor.LogExecutionTimeInterceptor;
import com.example.laboratorul8.model.*;
import com.example.laboratorul8.repository.CourseRepository;
import com.example.laboratorul8.repository.StudentRepository;
import com.example.laboratorul8.repository.TeacherRepository;
import com.example.laboratorul8.repository.UserRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Interceptors(LogExecutionTimeInterceptor.class)
@RequestScoped
public class AuthService {

    @Inject
    UserRepository userRepository;

    @Inject
    StudentRepository studentRepository;
    @Inject
    private CourseService courseService;
    @Inject
    private TeacherRepository teacherRepository;
    @Inject
    private CourseRepository courseRepository;

    public User login(String username, String password) {
        User user = userRepository.login(username, password);
        return user;
    }

    public boolean register(String name, String username, String password, String role, int year, int semester, List<String> courseSelected) {
        try {
            // Conversie din String în UserRole
            UserRole userRole = UserRole.valueOf(role.toUpperCase());

            if (userRole == UserRole.STUDENT) {

                Student student = new Student();
                student.setName(name);
                student.setUsername(username);
                student.setPassword(password);
                student.setUserRole(userRole);
                student.setYear(year);
                student.setSemester(semester);

                studentRepository.persist(student);
                return true;

            } else if(userRole == UserRole.TEACHER) {

                Set<Course> courseList = new HashSet<>();

                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setUsername(username);
                teacher.setPassword(password);
                teacher.setUserRole(userRole);


                for (String courseName : courseSelected) {

                    Course courseObject = courseService.findByName(courseName);

                    Set<Teacher> teachersForCourse = courseObject.getTeachers();

                    if (teachersForCourse == null) {
                        teachersForCourse = new HashSet<>();
                    }

                    teachersForCourse.add(teacher);
                    courseObject.setTeachers(teachersForCourse);

                    courseList.add(courseObject);

                    courseRepository.persist(courseObject);
                }


                teacher.setCourses(courseList);

                teacherRepository.persist(teacher);
                return true;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role: " + role);
        }
        return false;
    }

}
