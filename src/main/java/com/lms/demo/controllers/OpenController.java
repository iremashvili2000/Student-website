package com.lms.demo.controllers;

import com.lms.demo.exceptions.BadFormedDataException;
import com.lms.demo.jwt.JwtTokenProvider;
import com.lms.demo.models.Token;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.models.openmodel.Login;
import com.lms.demo.models.openmodel.Registration;
import com.lms.demo.repository.UserRepository;
import com.lms.demo.service.OpenService;
import com.lms.demo.service.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class OpenController {
    private final OpenService openService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public OpenController(OpenService openService, UserService userService, AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.openService = openService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/api/v1/open/auth/sign-up",method = RequestMethod.POST)
    public ResponseEntity<String> register(@Valid @RequestBody Registration registration){
        if(openService.registration(registration)){
            return new ResponseEntity(HttpStatus.OK);
        }
        throw new BadFormedDataException("User","user can't registration");
    }

    @RequestMapping(value = "/api/v1/open/auth/token",method = RequestMethod.POST)
    public Token signin(@Valid @RequestBody Login login) {
        Date bla=new Date();
        System.out.println(bla.getTime());

        User user = (User) userService.loadUserByUsername(login.getEmail());
        if(user==null){
            throw new BadFormedDataException("this password or username is not correct");
        }
        user.setLastUpdate(LocalDateTime.now());

        List<String> Roles = new ArrayList<>();
        Roles.add(user.getRole().toUpperCase());
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(),login.getPassword(),getGrantedAuthorities(Roles));

        authenticationManager.authenticate(authentication);
        String token = jwtTokenProvider.createToken(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Token token1=new Token(token);
        userRepository.save(user);
        return token1;
    }
    @RequestMapping(value = "/api/v1/open/about",method = RequestMethod.GET)
    public String getabout(){
      return  openService.getAbout();
    }



    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }







}
