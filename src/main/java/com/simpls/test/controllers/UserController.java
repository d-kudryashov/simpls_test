package com.simpls.test.controllers;

import com.simpls.test.entities.User;
import com.simpls.test.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PutMapping("/")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        if (Objects.nonNull(user)) {
            if (Objects.nonNull(user.getId()) && userRepository.existsById(user.getId()) || Objects.isNull(user.getId())) {
                User result = userRepository.save(user);
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(result);
            }
            if (Objects.nonNull(user.getId()) && !userRepository.existsById(user.getId())) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .build();
            }
        }
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity getUsers() {
        List<User> result = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            result.add(user);
        }
        if (result.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @GetMapping("{id}")
    @ResponseBody
    public ResponseEntity getUserById(@PathVariable int id) {
        Optional<User> optionalResult = userRepository.findById(id);
        if (optionalResult.isPresent()) {
            User result = optionalResult.get();
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(result);
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("{id}")
    @ResponseBody
    public ResponseEntity deleteUser(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }
}
