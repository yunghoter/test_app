package com.college;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalTime;
import java.util.List;

/**
 * Головний клас програми для роботи з базою даних розкладу коледжу.
 * 
 * <p>Цей клас використовує ORM фреймворк Hibernate для взаємодії з базою даних. Він виконує дві основні операції:
 * <ul>
 *   <li>Перша транзакція: Додавання даних до таблиць Departments, Teachers, Students, Courses, Rooms, ClassSchedules та Enrollments.</li>
 *   <li>Друга транзакція: Виконання запиту для отримання інформації про розклад занять студентів.</li>
 * </ul>
 * </p>
 * 
 * <p>Клас використовує наступні Entity класи таблиць бази даних:
 * <ul>
 *   <li>{@link Department}</li>
 *   <li>{@link Teacher}</li>
 *   <li>{@link Student}</li>
 *   <li>{@link Course}</li>
 *   <li>{@link Room}</li>
 *   <li>{@link ClassSchedule}</li>
 *   <li>{@link Enrollment}</li>
 * </ul>
 * </p>
 * 
 * <p>Запит у другій транзакції повертає об'єкти {@link ScheduleInfoDTO}, які містять інформацію про студентів, викладачів, курси, кафедри, аудиторії, семестри, роки та час занять.</p>
 * 
 * <p>Для запуску програми необхідно мати налаштований файл конфігурації Hibernate (hibernate.cfg.xml).</p>
 * 
 * @see Department
 * @see Teacher
 * @see Student
 * @see Course
 * @see Room
 * @see ClassSchedule
 * @see Enrollment
 * @see ScheduleInfoDTO
 */
public class MainApp {
    public static void main(String[] args) {
        // Створення фабрики сесій Hibernate
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

        // Перша транзакція: Додавання даних 
        try (Session session = factory.openSession()) {
            // Початок транзакції
            session.beginTransaction();

            // Додавання даних в Departments
            Department dept1 = new Department();
            dept1.setName("Комп`ютерні науки");
            dept1.setLocation("Корпус A");

            Department dept2 = new Department();
            dept2.setName("Математика");
            dept2.setLocation("Корпус B");

            session.save(dept1);
            session.save(dept2);

            // Додавання даних в Teachers
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

            // Додавання даних в Students
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

            // Додавання даних в Courses
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

            // Додавання даних в Rooms
            Room room1 = new Room();
            room1.setRoomNumber("210");
            room1.setCapacity(30);

            Room room2 = new Room();
            room2.setRoomNumber("212");
            room2.setCapacity(50);

            session.save(room1);
            session.save(room2);

            // Додавання даних в Class Schedules
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

            // Додавання даних в Enrollments
            // 3 студенти записані на 'Вступ до програмування'
            Enrollment enrollment1 = new Enrollment();
            enrollment1.setStudent(student1); // Аліса
            enrollment1.setCourse(course1);   // Вступ до програмування
            enrollment1.setEnrollmentDate(java.time.LocalDate.now());

            Enrollment enrollment2 = new Enrollment();
            enrollment2.setStudent(student3); // Катерина
            enrollment2.setCourse(course1);   // Вступ до програмування
            enrollment2.setEnrollmentDate(java.time.LocalDate.now());

            Enrollment enrollment3 = new Enrollment();
            enrollment3.setStudent(student4); // Дмитро
            enrollment3.setCourse(course1);   // Вступ до програмування
            enrollment3.setEnrollmentDate(java.time.LocalDate.now());

            session.save(enrollment1);
            session.save(enrollment2);
            session.save(enrollment3);

            // 2 студенти записані на 'Математичний аналіз I'
            Enrollment enrollment4 = new Enrollment();
            enrollment4.setStudent(student2); // Богдан
            enrollment4.setCourse(course2);   // Математичний аналіз I
            enrollment4.setEnrollmentDate(java.time.LocalDate.now());

            Enrollment enrollment5 = new Enrollment();
            enrollment5.setStudent(student5); // Олена
            enrollment5.setCourse(course2);   // Математичний аналіз I
            enrollment5.setEnrollmentDate(java.time.LocalDate.now());

            session.save(enrollment4);
            session.save(enrollment5);

            // Підтвердження транзакції
            session.getTransaction().commit();
            System.out.println("Дані успішно збережено!");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Друга транзакція: Виконання запиту
        try (Session session = factory.openSession()) {
            // Початок транзакції
            session.beginTransaction();

            // Виконання запиту
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

            // Обробка результатів
            for (ScheduleInfoDTO info : results) {
                System.out.println("Студент: " + info.getStudentFirstName() + " " + info.getStudentLastName());
                System.out.println("Викладач: " + info.getTeacherFirstName() + " " + info.getTeacherLastName());
                System.out.println("Курс: " + info.getCourseName());
                System.out.println("Кафедра: " + info.getDepartmentName());
                System.out.println("Аудиторія: " + info.getRoomNumber());
                System.out.println("Семестр: " + info.getSemester());
                System.out.println("Рік: " + info.getYear());
                System.out.println("Час початку: " + info.getStartTime());
                System.out.println("Час закінчення: " + info.getEndTime());
                System.out.println("--------------------------------------------------");
            }

            // Підтвердження транзакції
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }
    }
}