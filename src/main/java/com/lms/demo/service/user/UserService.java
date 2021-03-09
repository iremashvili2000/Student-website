package com.lms.demo.service.user;

import com.lms.demo.models.ChangeInfo;
import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String username);

    void deleteMe(User user);


    Lecture getFirstLecture(User user);
}
