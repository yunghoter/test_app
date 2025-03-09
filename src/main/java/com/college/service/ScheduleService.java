package com.college.service;

import com.college.ScheduleInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ScheduleService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ScheduleInfoDTO> getScheduleInfo() {
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
                
        return entityManager.createQuery(hql, ScheduleInfoDTO.class).getResultList();
    }
}