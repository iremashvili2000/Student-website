package com.lms.demo.controllers;

import com.lms.demo.exceptions.BadFormedDataException;
import com.lms.demo.exceptions.DontFoundException;
import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.Room;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.models.foradmin.AddStudent;
import com.lms.demo.models.foradmin.Meil;
import com.lms.demo.models.foradmin.SetLecture;
import com.lms.demo.models.openmodel.Registration;
import com.lms.demo.service.OpenService;
import com.lms.demo.service.admin.AdminService;
import com.lms.demo.service.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final AdminService adminService;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final OpenService openService;

    public AdminController(AdminService adminService, UserService userService, BCryptPasswordEncoder passwordEncoder, OpenService openService) {
        this.adminService = adminService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.openService = openService;
    }

    @RequestMapping(value = "/admin/users/list",method = RequestMethod.POST)
    public List<User> getUsers(@AuthenticationPrincipal UserDetails userDetails){
        User user=userService.findByEmail(userDetails.getUsername());

            return adminService.usersList();



    }

    @RequestMapping(value = "/admin/setRole",method = RequestMethod.POST)
    public User setUserRole(@AuthenticationPrincipal UserDetails userDetails,@Valid @RequestBody Meil mail){
        User user=userService.findByEmail(userDetails.getUsername());


            if (!passwordEncoder.matches(mail.getAdminPassword(), user.getPassword())) {
                throw new BadFormedDataException("password", "password is not correct");

            }
            return adminService.setRole(mail.getMeil());



    }
    @RequestMapping(value = "/admin/add/room/{name}",method = RequestMethod.POST)
    public Room addRoom(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name = "name")String name){
       return adminService.addRoom(name);
    }

    @RequestMapping(value = "/admin/lecture/add/student",method =RequestMethod.POST )
    public List<User> addstudent(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddStudent addStudent){
       return adminService.addStudent(addStudent.getGmail(), addStudent.getSubject());
    }

    @RequestMapping(value = "/admin/add/Lecture",method = RequestMethod.POST)
    public List<Lecture> setLecture(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody SetLecture setLecture){

       return  adminService.setLecture(setLecture);
    }
    @RequestMapping(value = "/admin/delete/lecture/{name}",method = RequestMethod.DELETE)
    public String deleteLecture(@AuthenticationPrincipal UserDetails userDetails,@PathVariable(name = "name")String lecturename){
       return adminService.deleteLecture(lecturename);
    }

    @RequestMapping(value = "/admin/lectures",method = RequestMethod.POST)
    public List<Lecture> getlectures(@AuthenticationPrincipal UserDetails userDetails){
        return adminService.getlectures();
    }

    @RequestMapping(value = "/admin/add/student",method = RequestMethod.POST)
    public boolean addStudentBase(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Registration registration){
        if(openService.registration(registration)){
            return true;
        }
        throw new BadFormedDataException("User","cant registration");

    }

    @RequestMapping(value = "/admin/delete/student/{gmail}",method = RequestMethod.DELETE)
    public String deleteUser(@AuthenticationPrincipal UserDetails userDetails,@PathVariable(name = "gmail") String gmail){
     return   adminService.deleteUser(gmail);
    }
    @RequestMapping(value = "/admin/lecture/change/{time}/{lname}",method = RequestMethod.POST)
    public Lecture changeTime(@AuthenticationPrincipal UserDetails userDetails, @PathVariable(name="time")String time,@PathVariable(name = "lname")String lecturename)
    {
        LocalDateTime local=LocalDateTime.parse(time);
        System.out.println("hello world");
       return adminService.changeTime(local,lecturename);

    }

    @RequestMapping(value = "/admin/room/list",method = RequestMethod.POST)
    public List<Room> getRoomList(@AuthenticationPrincipal UserDetails userDetails){
       if(adminService.getRoomList()!=null){
           return adminService.getRoomList();
       }
       throw new DontFoundException("room","rooms is not found");
    }

    @RequestMapping(value = "/admin/room/delete/{roomname}",method = RequestMethod.DELETE)
    public String deleteRoom(@AuthenticationPrincipal UserDetails userDetails,@PathVariable(name = "roomname")String roomname){
       if(adminService.deleteRoom(roomname)!=null){
           return adminService.deleteRoom(roomname);
       }
       throw new DontFoundException("form","dont found");


    }

    @RequestMapping(value = "/admin/admins/list",method = RequestMethod.POST)
    public List<User> getAdminList(@AuthenticationPrincipal UserDetails userDetails){
      return  adminService.getAdminList();
    }



}
