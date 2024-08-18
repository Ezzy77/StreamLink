package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.elisiocabral.stream_link.model.User;

@Service
public class UserService{
    UserRepository userRepository;


    public User save(User user){
        return userRepository.save(user);
    }

}
