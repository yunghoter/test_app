package com.college.controller;

import com.college.ClassSchedule;
import com.college.Course;
import com.college.Room;
import com.college.Teacher;
import com.college.ScheduleInfoDTO;
import com.college.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final StudentService studentService;
    private final TeacherService teacherService;
    private final CourseService courseService;
    private final RoomService roomService;
    private final ClassScheduleService classScheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService,
                            StudentService studentService,
                            TeacherService teacherService,
                            CourseService courseService,
                            RoomService roomService,
                            ClassScheduleService classScheduleService) {
        this.scheduleService = scheduleService;
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
        this.roomService = roomService;
        this.classScheduleService = classScheduleService;
    }

    @GetMapping("/list")
    public String listSchedule(Model model) {
        List<ScheduleInfoDTO> scheduleInfo = scheduleService.getScheduleInfo();
        model.addAttribute("schedules", scheduleInfo);
        return "schedule/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("rooms", roomService.findAll());
        model.addAttribute("classSchedule", new ClassSchedule());
        return "schedule/add-form";
    }

    @PostMapping("/add")
    public String addSchedule(
            @RequestParam("course") Integer courseId,
            @RequestParam("teacher") Integer teacherId,
            @RequestParam("room") Integer roomId,
            @RequestParam("semester") String semester,
            @RequestParam("year") Integer year,
            @RequestParam("startTime") String startTime,
            @RequestParam("endTime") String endTime) {
        
        ClassSchedule classSchedule = new ClassSchedule();
        classSchedule.setCourse(courseService.findById(courseId.longValue()).orElse(null));
        classSchedule.setTeacher(teacherService.findById(teacherId.longValue()).orElse(null));
        classSchedule.setRoom(roomService.findById(roomId.longValue()).orElse(null));
        classSchedule.setSemester(semester);
        classSchedule.setYear(year);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        classSchedule.setStartTime(LocalTime.parse(startTime, formatter));
        classSchedule.setEndTime(LocalTime.parse(endTime, formatter));

        classScheduleService.save(classSchedule);
        return "redirect:/schedule/list";
    }
}