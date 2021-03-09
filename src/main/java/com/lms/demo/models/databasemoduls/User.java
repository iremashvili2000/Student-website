package com.lms.demo.models.databasemoduls;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="students")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    @Column(name="name")
    private String name;
    @NotNull
    @Column(name="lastname")
    private String lastname;
    @NotNull
    @Column(name="username")
    private String username;
    @NotNull
    @JsonIgnore
    @Column(name="password")
    private String password;
    @Email
    @Column(name="email",unique = true)
    private String email;
    @Column(name="role")
    @NotNull
    private String role;
    @Column(name = "lastUpdate")
    @JsonFormat(pattern ="yyyy-MM-dd HH:mm:ss" )
    @JsonIgnore
    private  LocalDateTime  lastUpdate=LocalDateTime.now();



    @NotNull
    @Column(name="your_id")
    @JsonIgnore
    private Long your_id;
    private boolean active=false;


    @OneToMany
    @JoinTable(name = "lectureStudents",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    @JsonIgnore
    private List<Lecture> Lecture;



    public User() {
    }

    public User(@NotNull Long your_id,@NotNull String name, @NotNull String lastname, @NotNull String username, @NotNull String password, @Email String email, @NotNull String role, boolean active) {
        this.name = name;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.active = active;
        this.your_id=your_id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @JsonIgnore
    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public Long getYour_id() {
        return your_id;
    }

    public void setYour_id(Long your_id) {
        this.your_id = your_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
        @JsonIgnore
    public List<com.lms.demo.models.databasemoduls.Lecture> getLecture() {
        return Lecture;
    }

    public void setLecture(List<com.lms.demo.models.databasemoduls.Lecture> lecture) {
        Lecture = lecture;
    }

    /////
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        String ROLE_PREFIX="ROLE_";
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));

        return list;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
