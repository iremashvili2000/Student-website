package com.lms.demo.repository;

import com.lms.demo.models.databasemoduls.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface LectureRepository extends JpaRepository<Lecture,Long> {
    Lecture findByLectureName(String lecturename);

    Lecture findLectureByLocalDateTime(LocalDateTime localDateTime);
}
