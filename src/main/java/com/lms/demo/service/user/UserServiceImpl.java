package com.lms.demo.service.user;

import com.lms.demo.exceptions.BadFormedDataException;
import com.lms.demo.exceptions.DontFoundException;
import com.lms.demo.models.databasemoduls.Lecture;
import com.lms.demo.models.databasemoduls.User;
import com.lms.demo.repository.LectureRepository;
import com.lms.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;


    public UserServiceImpl(UserRepository userRepository, LectureRepository lectureRepository) {
        this.userRepository = userRepository;

        this.lectureRepository = lectureRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user= (User) userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Dont Found "+email);
        }

        return (UserDetails) user;
    }

    @Override
    public User findByEmail(String email) {
      User user=userRepository.findByEmail(email);
      if(user==null){
          throw new UsernameNotFoundException("your email is not correct");
      }
        return user;
    }

    @Override
    public void deleteMe(User user) {
        for(User u:userRepository.findAll()){
            u.getLecture().remove(this);
        }

        userRepository.deleteById(user.getId());
    }

    @Override
    public Lecture getFirstLecture(User user) {
       List<Lecture>lectureList= user.getLecture();
        LocalDateTime localDateTime=LocalDateTime.MAX;
        LocalDateTime local=LocalDateTime.MAX;
       for(int i=0;i< lectureList.size();i++){
           if(lectureList.get(i).getLocalDateTime().minusDays(user.getLastUpdate().getDayOfYear()).minusHours(user.getLastUpdate().getHour()).minusMinutes(user.getLastUpdate().getMinute()).isBefore(localDateTime)){
               localDateTime=lectureList.get(i).getLocalDateTime().minusDays(user.getLastUpdate().getDayOfYear()).minusHours(user.getLastUpdate().getHour()).minusMinutes(user.getLastUpdate().getMinute());
                local=lectureList.get(i).getLocalDateTime();
           }

       }
       Lecture lecture=lectureRepository.findLectureByLocalDateTime(local);
       if(lecture==null){
           throw new DontFoundException("lecture","lecture dont found");
       }

       return lecture;
    }


}
