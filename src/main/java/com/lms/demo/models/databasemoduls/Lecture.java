package com.lms.demo.models.databasemoduls;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import org.jboss.resteasy.annotations.providers.jaxb.Formatted;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "Lecture")
public class Lecture {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String lectureName;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime localDateTime;


    @ManyToMany
    @JoinTable(name="lectureStudents",
            joinColumns = @JoinColumn(name="lecture_id"),
            inverseJoinColumns = @JoinColumn(name="user_id"))

    private List<User> user;

    @OneToOne
    private Room room;

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }


    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
