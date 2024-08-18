package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elisiocabral.stream_link.model.User;

@Service
public class UserService{


    @Autowired
    UserRepository userRepository;


    public User save(User user){
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User update(User user){
        return userRepository.save(user);
    }



}
