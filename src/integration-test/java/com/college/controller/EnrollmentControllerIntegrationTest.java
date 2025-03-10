package com.college.controller;

import com.college.MainApp;
import com.college.entity.Course;
import com.college.entity.Department;
import com.college.entity.Student;
import com.college.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Інтеграційний тест контролера реєстрації на курси
@SpringBootTest(
    classes = MainApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private DepartmentService departmentService;

    private Student student;
    private Course course;

    // Підготовка тестових даних
    @BeforeEach
    void setUp() {
        // Створення тестової кафедри
        Department department = new Department();
        department.setName("Комп'ютерні науки");
        department.setLocation("Корпус А");
        departmentService.save(department);

        // Створення тестового студента
        student = new Student();
        student.setFirstName("Марія");
        student.setLastName("Коваленко");
        student.setDepartment(department);
        studentService.save(student);

        // Створення тестового курсу
        course = new Course();
        course.setCourseName("Програмування Java");
        course.setCredits(3);
        course.setDepartment(department);
        courseService.save(course);
    }

    // Тест відображення форми реєстрації
    @Test
    void showEnrollmentForm_ShouldDisplayForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/enrollment/enroll"))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollment/enroll-form"))
                .andExpect(model().attributeExists("enrollment"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"));
    }

    // Тест успішної реєстрації на курс
    @Test
    void enroll_ShouldCreateEnrollmentAndRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/enroll")
                .param("student", student.getStudentId().toString())
                .param("course", course.getCourseId().toString()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule/list"));
    }

    // Тест валідації даних при реєстрації
    @Test
    void enroll_WithInvalidData_ShouldReturnToForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/enrollment/enroll")
                .param("student", "")
                .param("course", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("enrollment/enroll-form"))
                .andExpect(model().attributeExists("enrollment"))
                .andExpect(model().attributeExists("students"))
                .andExpect(model().attributeExists("courses"));
    }
}