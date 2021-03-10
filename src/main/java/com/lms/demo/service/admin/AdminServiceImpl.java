package com.lms.demo.service.admin;

import com.lms.demo.exceptions.BadFormedDataException;
import com.lms.demo.exceptions.DontFoundException;
import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.Room;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.models.foradmin.SetLecture;
import com.lms.demo.repository.LectureRepository;
import com.lms.demo.repository.RoomRepository;
import com.lms.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

@Service
public class AdminServiceImpl implements AdminService{
private final UserRepository userRepository;
private final RoomRepository roomRepository;
private final LectureRepository lectureRepository;

    public AdminServiceImpl(UserRepository userRepository, RoomRepository roomRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.lectureRepository = lectureRepository;
    }

    @Override
    public List<User> usersList() {
        List<User>users=userRepository.findAll();
        List<User>realUsers=new ArrayList<User>();
        for(int i=0;i<users.size();i++){
            if(users.get(i).getRole().equals("USER")){
                realUsers.add(users.get(i));
            }
        }
        return realUsers;
    }

    @Override
    public User setRole(String meil) {
        User user= userRepository.findByEmail(meil);
        if(user==null){
            throw new DontFoundException("email","dont found this username");
        }
        user.setRole("ADMIN");
        return user;
    }

    @Override
    public List<Lecture> setLecture(SetLecture setLecture) {
        if(lectureRepository.findByLectureName(setLecture.getLecturename())==null){
            Lecture lecture=new Lecture();
            lecture.setLectureName(setLecture.getLecturename());

            Room room= roomRepository.findByRoomName(setLecture.getRoomname());
            lecture.setRoom(room);
            lectureRepository.save(lecture);
            List<Lecture> lecturelist=lectureRepository.findAll();
            return lecturelist;
        }
        Lecture lecture= lectureRepository.findByLectureName(setLecture.getLecturename());
        Room room= roomRepository.findByRoomName(setLecture.getRoomname());
        if(room==null){
            throw new DontFoundException("room","room dont found");
        }
        lecture.setRoom(room);
        lectureRepository.save(lecture);
        List<Lecture>lectureList=lectureRepository.findAll();
        return lectureList;
    }

    @Override
    public Room addRoom(String name) {
        if(name.isEmpty()){
            throw new BadFormedDataException("room","please write room name");
        }
        if(roomRepository.findByRoomName(name)!=null){
            throw new BadFormedDataException("former","this room already use");
        }
        Room room=new Room();
        room.setRoomName(name);
        System.out.println(room);
        roomRepository.save(room);
        return room;
    }

    @Override
    public List<User> addStudent(String gmail, String subject) {
       Lecture lecture=lectureRepository.findByLectureName(subject);
       if(lecture==null){
           throw  new DontFoundException("lecture","lecture dont found");
       }

       User user= userRepository.findByEmail(gmail);
       if(user==null){
           throw new DontFoundException("user","user dont found");
       }
       List<User>userList=new ArrayList<>();
    userList=lecture.getUser();
    userList.add(user);
    System.out.println(user);
     lecture.setUser(userList);
     lectureRepository.save(lecture);
     return lecture.getUser();

    }

    @Override
    public List<Lecture> getlectures() {
       return lectureRepository.findAll();
    }

    @Override
    @PreRemove
    public String deleteUser(String gmail) {
        User user=userRepository.findByEmail(gmail);
        if(user.getRole().equals("ADMIN")){
            throw  new DontFoundException("admin","you cant delete admin");
        }
        if(user==null) {
            throw new DontFoundException("user", "user dont found");
        }
        for(User u:userRepository.findAll()){
            u.getLecture().remove(this);
        }

        userRepository.deleteUserById(user.getId());
        return "deleted";
    }

    @Override
    public Lecture changeTime(LocalDateTime time, String lecturename) {
        Lecture lecture= lectureRepository.findByLectureName(lecturename);
        if(lecture==null){
            throw new DontFoundException("lecture","lecture dont found");
        }

        lecture.setLocalDateTime(time);
        lectureRepository.save(lecture);
        return lecture;
    }

    @Override
    public String deleteLecture(String lecturename) {
        Lecture lecture= lectureRepository.findByLectureName(lecturename);
        if(lecture==null){
            throw new DontFoundException("lecute","lecture dont found");
        }
        lectureRepository.deleteById(lecture.getId());
        return "deleted";

    }

    @Override
    public List<Room> getRoomList() {
        return roomRepository.findAll();
    }

    @Override
    public String deleteRoom(String roomname) {
        Room room=roomRepository.findByRoomName(roomname);
        if(room==null){
            throw new DontFoundException("room","room dont found");
        }
        roomRepository.deleteById(room.getId());
        roomRepository.deleteById(room.getId());
        return "deleted";
    }

    @Override
    public List<User> getAdminList() {
        List<User>userList=userRepository.findAll();
        List<User>adminlist=new ArrayList<>();
        for (int i=0;i<userList.size();i++){
            if(userList.get(i).getRole().equals("ADMIN")){
                adminlist.add(userList.get(i));
            }
        }
        return adminlist;
    }

}
