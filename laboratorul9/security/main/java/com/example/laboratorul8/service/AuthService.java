package com.example.laboratorul8.service;

import com.example.laboratorul8.config.PasswordUtil;
import com.example.laboratorul8.interceptor.LogExecutionTimeInterceptor;
import com.example.laboratorul8.model.*;
import com.example.laboratorul8.repository.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

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
    private GroupRepository groupRepository;
    @Inject
    private CourseRepository courseRepository;

    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext externalContext = context.getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

    public User login(String username, String password) throws ServletException {

        try {
            request.login(username, password);

            User user = userRepository.login(username, password);
            if (user == null) {
                throw new RuntimeException("Username sau parolă incorectă.");
            }
            return user;

        } catch (ServletException e) {
            throw new ServletException("Eroare de autentificare: " + e.getMessage(), e);
        } catch (RuntimeException e) {
            throw new RuntimeException("Eroare aplicație: " + e.getMessage(), e);
        }

    }

    public boolean register(String name, String username, String password, String role, int year, int semester, List<String> courseSelected) {
        try {
            // Conversie din String în UserRole
            UserRole userRole = UserRole.valueOf(role.toUpperCase());

            if (userRole == UserRole.STUDENT) {

                Student student = new Student();
                student.setName(name);
                student.setUsername(username);
                String hashedPassword = PasswordUtil.hashPassword(password);
                student.setPassword(hashedPassword);
//                student.setPassword(password);
                student.setUserRole(userRole);
                student.setYear(year);
                student.setSemester(semester);

                Group studentGroup = new Group();
                studentGroup.setUsername(username);
                studentGroup.setGroupName("student");

                groupRepository.persist(studentGroup);
                studentRepository.persist(student);
                return true;

            } else if(userRole == UserRole.TEACHER) {

                Set<Course> courseList = new HashSet<>();

                Teacher teacher = new Teacher();
                teacher.setName(name);
                teacher.setUsername(username);
                String hashedPassword = PasswordUtil.hashPassword(password);
                teacher.setPassword(hashedPassword);
//                teacher.setPassword(password);
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

                Group teacherGroup = new Group();
                teacherGroup.setUsername(username);
                teacherGroup.setGroupName("teacher");

                groupRepository.persist(teacherGroup);

                teacherRepository.persist(teacher);
                return true;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid role: " + role);
        }
        return false;
    }

}
