package com.elisiocabral.stream_link.controller;

import com.elisiocabral.stream_link.dto.UserDTO;
import com.elisiocabral.stream_link.model.User;
import com.elisiocabral.stream_link.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Iterable<User> getUser() {
        return userService.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody UserDTO userDTO) {
        return userService.save(userDTO);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
    }





}
