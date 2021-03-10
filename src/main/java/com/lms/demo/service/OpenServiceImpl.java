package com.lms.demo.service;

import com.lms.demo.exceptions.BadFormedDataException;

import com.lms.demo.models.openmodel.Registration;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OpenServiceImpl implements OpenService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public OpenServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean registration(Registration registration) {
        if(!registration.getPassword().equals(registration.getRepassword())){
            throw new BadFormedDataException("Password","Password doesn't matches");
        }
        if(userRepository.findByEmail(registration.getEmail())!=null){
            throw new BadFormedDataException("email","email must be unique");
        }
        if(userRepository.findByUsername(registration.getUsername())!=null){
            throw new BadFormedDataException("username","username must be unique");
        }
        if(registration.getPassword().length()<6){
            throw new BadFormedDataException("password","password's length must be larger than 6");
        }
        if(registration.getYour_id()==null || registration.getUsername()==null || registration.getEmail()==null || registration.getLastname()==null || registration.getName()==null || registration.getPassword()==null || registration.getRepassword()==null){
            throw new BadFormedDataException("value","please write all line");

        }

        if(registration.getYour_id().toString().length()!=11){
            throw new BadFormedDataException("your id","your id's size must be 11");
        }

        User user=new User();
        user.setActive(true);
        user.setEmail(registration.getEmail());
        user.setLastname(registration.getLastname());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setName(registration.getName());
        user.setUsername(registration.getUsername());
        user.setRole("ADMIN");
        user.setYour_id(registration.getYour_id());
        userRepository.save(user);
        return true;
    }

    @Override
    public String getAbout() {
        return "we are facebook team we need build websit which connect people in whole earth";
    }
}
