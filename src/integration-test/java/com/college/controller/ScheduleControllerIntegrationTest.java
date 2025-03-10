package com.college.controller;

import com.college.*;
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

@SpringBootTest(
    classes = MainApp.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class ScheduleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RoomService roomService;

    private Department department;
    private Teacher teacher;
    private Course course;
    private Room room;

    @BeforeEach
    void setUp() {
        department = new Department();
        department.setName("Computer Science");
        department.setLocation("Building A");
        departmentService.save(department);

        teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setDepartment(department);
        teacherService.save(teacher);

        course = new Course();
        course.setCourseName("Java Programming");
        course.setCredits(3);
        course.setDepartment(department);
        courseService.save(course);

        room = new Room();
        room.setRoomNumber("101");
        room.setCapacity(30);
        roomService.save(room);
    }

    @Test
    void listSchedule_ShouldDisplaySchedulePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedule/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule/list"))
                .andExpect(model().attributeExists("schedules"));
    }

    @Test
    void showAddForm_ShouldDisplayAddForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/schedule/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedule/add-form"))
                .andExpect(model().attributeExists("teachers"))
                .andExpect(model().attributeExists("courses"))
                .andExpect(model().attributeExists("rooms"))
                .andExpect(model().attributeExists("classSchedule"));
    }

    @Test
    void addSchedule_ShouldCreateNewScheduleAndRedirect() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/schedule/add")
                .param("course", course.getCourseId().toString())
                .param("teacher", teacher.getTeacherId().toString())
                .param("room", room.getRoomId().toString())
                .param("semester", "Fall")
                .param("year", "2024")
                .param("startTime", "09:00")
                .param("endTime", "10:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/schedule/list"));
    }

    @Test
    void addSchedule_WithInvalidData_ShouldReturnToForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/schedule/add")
                .param("course", "")
                .param("teacher", "")
                .param("room", "")
                .param("semester", "")
                .param("year", "invalid")
                .param("startTime", "invalid")
                .param("endTime", "invalid"))
                .andExpect(status().is3xxRedirection());
    }
}