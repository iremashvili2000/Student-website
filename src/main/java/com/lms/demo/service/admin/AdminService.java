package com.lms.demo.service.admin;

import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.Room;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.models.foradmin.SetLecture;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {
    List<User> usersList();

    User setRole(String meil);

    List<Lecture> setLecture(SetLecture setLecture);

    Room addRoom(String name);

    List<User> addStudent(String name, String subject);

    List<Lecture> getlectures();

    String deleteUser(String gmail);

    Lecture changeTime(LocalDateTime time, String lecturename);

    String deleteLecture(String lecturename);

    List<Room> getRoomList();

    String deleteRoom(String roomname);

    List<User> getAdminList();
}
