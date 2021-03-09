package com.lms.demo.repository;

import com.lms.demo.models.databasemoduls.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room,Long>{

    Room findByRoomName(String name);
    void deleteById(long id);
}
