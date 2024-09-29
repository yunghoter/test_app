package com.college;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalTime;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        // Create Hibernate session factory
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Department.class)
                .addAnnotatedClass(Teacher.class)
                .addAnnotatedClass(Student.class)
                .addAnnotatedClass(Course.class)
                .addAnnotatedClass(Room.class)
                .addAnnotatedClass(ClassSchedule.class)
                .addAnnotatedClass(Enrollment.class)
                .buildSessionFactory();

        // First transaction: Insert data
        try (Session session = factory.openSession()) {
            // Begin transaction
            session.beginTransaction();

            // Inserting into Departments
            Department dept1 = new Department();
            dept1.setName("Комп`ютерні науки");
            dept1.setLocation("Корпус A");

            Department dept2 = new Department();
            dept2.setName("Математика");
            dept2.setLocation("Корпус B");

            session.save(dept1);
            session.save(dept2);

            // Inserting into Teachers
            Teacher teacher1 = new Teacher();
            teacher1.setFirstName("Іван");
            teacher1.setLastName("Петренко");
            teacher1.setDepartment(dept1); // Комп'ютерні науки

            Teacher teacher2 = new Teacher();
            teacher2.setFirstName("Оксана");
            teacher2.setLastName("Коваль");
            teacher2.setDepartment(dept2); // Математика

            session.save(teacher1);
            session.save(teacher2);

            // Inserting into Students
            Student student1 = new Student();
            student1.setFirstName("Аліса");
            student1.setLastName("Мельник");
            student1.setDepartment(dept1); // Комп'ютерні науки

            Student student2 = new Student();
            student2.setFirstName("Богдан");
            student2.setLastName("Іванов");
            student2.setDepartment(dept2); // Математика

            Student student3 = new Student();
            student3.setFirstName("Катерина");
            student3.setLastName("Левченко");
            student3.setDepartment(dept1); // Комп'ютерні науки

            Student student4 = new Student();
            student4.setFirstName("Дмитро");
            student4.setLastName("Шевченко");
            student4.setDepartment(dept1); // Комп'ютерні науки

            Student student5 = new Student();
            student5.setFirstName("Олена");
            student5.setLastName("Петренко");
            student5.setDepartment(dept2); // Математика

            session.save(student1);
            session.save(student2);
            session.save(student3);
            session.save(student4);
            session.save(student5);

            // Inserting into Courses
            Course course1 = new Course();
            course1.setCourseName("Вступ до програмування");
            course1.setDepartment(dept1); // Комп'ютерні науки
            course1.setCredits(3);

            Course course2 = new Course();
            course2.setCourseName("Математичний аналіз I");
            course2.setDepartment(dept2); // Математика
            course2.setCredits(4);

            session.save(course1);
            session.save(course2);

            // Inserting into Rooms
            Room room1 = new Room();
            room1.setRoomNumber("210");
            room1.setCapacity(30);

            Room room2 = new Room();
            room2.setRoomNumber("212");
            room2.setCapacity(50);

            session.save(room1);
            session.save(room2);

            // Inserting into Class Schedules
            ClassSchedule schedule1 = new ClassSchedule();
            schedule1.setCourse(course1);
            schedule1.setTeacher(teacher1);
            schedule1.setRoom(room1);
            schedule1.setSemester("Осінь");
            schedule1.setYear(2024);
            schedule1.setStartTime(LocalTime.parse("09:00:00"));
            schedule1.setEndTime(LocalTime.parse("10:30:00"));

            ClassSchedule schedule2 = new ClassSchedule();
            schedule2.setCourse(course2);
            schedule2.setTeacher(teacher2);
            schedule2.setRoom(room2);
            schedule2.setSemester("Осінь");
            schedule2.setYear(2024);
            schedule2.setStartTime(LocalTime.parse("11:00:00"));
            schedule2.setEndTime(LocalTime.parse("12:30:00"));

            session.save(schedule1);
            session.save(schedule2);

            // Inserting into Enrollments
            // 3 students enrolled in 'Вступ до програмування'
            Enrollment enrollment1 = new Enrollment();
            enrollment1.setStudent(student1); // Аліса
            enrollment1.setCourse(course1);   // Вступ до програмування

            Enrollment enrollment2 = new Enrollment();
            enrollment2.setStudent(student3); // Катерина
            enrollment2.setCourse(course1);   // Вступ до програмування

            Enrollment enrollment3 = new Enrollment();
            enrollment3.setStudent(student4); // Дмитро
            enrollment3.setCourse(course1);   // Вступ до програмування

            session.save(enrollment1);
            session.save(enrollment2);
            session.save(enrollment3);

            // 2 students enrolled in 'Математичний аналіз I'
            Enrollment enrollment4 = new Enrollment();
            enrollment4.setStudent(student2); // Богдан
            enrollment4.setCourse(course2);   // Математичний аналіз I

            Enrollment enrollment5 = new Enrollment();
            enrollment5.setStudent(student5); // Олена
            enrollment5.setCourse(course2);   // Математичний аналіз I

            session.save(enrollment4);
            session.save(enrollment5);

            // Commit the transaction
            session.getTransaction().commit();
            System.out.println("Data saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Second transaction: Execute query
        try (Session session = factory.openSession()) {
            // Start a transaction
            session.beginTransaction();

            // Execute the query
            // Execute the query
            String hql = "SELECT new com.college.ScheduleInfoDTO(" +
                    "s.firstName, s.lastName, " +
                    "t.firstName, t.lastName, " +
                    "c.courseName, d.name, " +
                    "r.roomNumber, cs.semester, " +
                    "cs.year, cs.startTime, cs.endTime) " +
                    "FROM Enrollment e " +
                    "JOIN e.student s " +
                    "JOIN e.course c " +
                    "JOIN c.classSchedules cs " +
                    "JOIN cs.teacher t " +
                    "JOIN c.department d " +
                    "JOIN cs.room r";

            List<ScheduleInfoDTO> results = session.createQuery(hql, ScheduleInfoDTO.class).getResultList();

            // Process the results
            for (ScheduleInfoDTO info : results) {
                System.out.println("Student: " + info.getStudentFirstName() + " " + info.getStudentLastName());
                System.out.println("Teacher: " + info.getTeacherFirstName() + " " + info.getTeacherLastName());
                System.out.println("Course: " + info.getCourseName());
                System.out.println("Department: " + info.getDepartmentName());
                System.out.println("Room: " + info.getRoomNumber());
                System.out.println("Semester: " + info.getSemester());
                System.out.println("Year: " + info.getYear());
                System.out.println("Start Time: " + info.getStartTime());
                System.out.println("End Time: " + info.getEndTime());
                System.out.println("--------------------------------------------------");
            }

            // Commit the transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}