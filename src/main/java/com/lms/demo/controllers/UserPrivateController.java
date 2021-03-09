package com.lms.demo.controllers;


import com.lms.demo.exceptions.BadFormedDataException;
import com.lms.demo.models.ChangeInfo;
import com.lms.demo.models.Password;
import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.service.user.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserPrivateController {
      private final  UserService userService;
      private final BCryptPasswordEncoder passwordEncoder;

    public UserPrivateController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @RequestMapping(value = "user/private/me",method = RequestMethod.POST)
    public User getUser(@AuthenticationPrincipal UserDetails userDetails){
        User user=userService.findByEmail(userDetails.getUsername());
        if(user==null){
            throw new BadFormedDataException("page","The link is unavailable we work");
        }
        return user;
    }

    @RequestMapping(value = "/user/private/deleteAccount",method = RequestMethod.DELETE)
    public String deleteMe(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody Password password){
        User user=userService.findByEmail(userDetails.getUsername());
        if(passwordEncoder.matches(password.getPassword(), user.getPassword())){
            userService.deleteMe(user);
            return "deleted";
        }
        throw new BadFormedDataException("password","your password is not correct");
    }

    @RequestMapping(value = "user/private/changeInfo",method = RequestMethod.PUT)
    public User changeInfo(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangeInfo changeInfo){
        User user=userService.findByEmail(userDetails.getUsername());
        if(passwordEncoder.matches(changeInfo.getOldpassword(), user.getPassword()) && changeInfo.getNewPassword().equals(changeInfo.getRenewPassword())){
           if(!changeInfo.getNewPassword().equals(changeInfo.getRenewPassword())){
               throw new BadFormedDataException("passwords","your new passwords is not equal");
           }
            user.setPassword(passwordEncoder.encode(changeInfo.getNewPassword()));
           user.setEmail(changeInfo.getNewemail());
           return user;
        }

        throw new BadFormedDataException("password","your old password is not correct");


    }

    @RequestMapping(value="user/private/lecture/notification",method = RequestMethod.POST)
    public Lecture getfirstLecture(@AuthenticationPrincipal UserDetails userDetails){
        User user=userService.findByEmail(userDetails.getUsername());
       return userService.getFirstLecture(user);
    }







}
