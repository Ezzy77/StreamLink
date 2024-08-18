package com.elisiocabral.stream_link.service;

import com.elisiocabral.stream_link.dto.UserDTO;
import com.elisiocabral.stream_link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elisiocabral.stream_link.model.User;

import java.sql.Timestamp;
import java.util.UUID;

@Service
public class UserService{

    UserRepository userRepository;


    public User save(UserDTO userDTO){
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Ensure this is hashed before storing
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return userRepository.save(user);
    }

    public User findById(UUID id){
        return userRepository.findById(id).orElse(null);
    }

    public void deleteById(UUID id){
        userRepository.deleteById(id);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public User update(User user){
        return userRepository.save(user);
    }



}
