package com.lms.demo.models.databasemoduls;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name="room")
public class Room {
    @NotNull
    private String roomName;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Room() {
    }

    public Room(@NotNull String roomName, long id) {
        this.roomName = roomName;
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
