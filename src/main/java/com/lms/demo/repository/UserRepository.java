package com.lms.demo.repository;

import com.lms.demo.models.databasemoduls.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    void deleteUserById(long id);

    User findByUsername(String username);
}
