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
import java.time.format.DateTimeParseException;
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
            @RequestParam(value = "course", required = false) Long courseId,
            @RequestParam(value = "teacher", required = false) Long teacherId,
            @RequestParam(value = "room", required = false) Long roomId,
            @RequestParam(value = "semester", required = false) String semester,
            @RequestParam(value = "year", required = false) String yearStr,
            @RequestParam(value = "startTime", required = false) String startTime,
            @RequestParam(value = "endTime", required = false) String endTime,
            Model model) {
        
        try {
            if (courseId == null || teacherId == null || roomId == null || 
                semester == null || yearStr == null || 
                startTime == null || endTime == null || 
                semester.trim().isEmpty() || yearStr.trim().isEmpty() ||
                startTime.trim().isEmpty() || endTime.trim().isEmpty()) {
                throw new IllegalArgumentException("All fields are required");
            }

            Integer year = Integer.parseInt(yearStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime parsedStartTime = LocalTime.parse(startTime, formatter);
            LocalTime parsedEndTime = LocalTime.parse(endTime, formatter);
            
            ClassSchedule classSchedule = new ClassSchedule();
            classSchedule.setCourse(courseService.findById(courseId).orElseThrow());
            classSchedule.setTeacher(teacherService.findById(teacherId).orElseThrow());
            classSchedule.setRoom(roomService.findById(roomId).orElseThrow());
            classSchedule.setSemester(semester);
            classSchedule.setYear(year);
            classSchedule.setStartTime(parsedStartTime);
            classSchedule.setEndTime(parsedEndTime);

            classScheduleService.save(classSchedule);
            return "redirect:/schedule/list";
            
        } catch (Exception e) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("teachers", teacherService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("rooms", roomService.findAll());
            model.addAttribute("classSchedule", new ClassSchedule());
            return "schedule/add-form";
        }
    }
}