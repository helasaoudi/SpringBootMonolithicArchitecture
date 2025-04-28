package com.example.demo.user.controller;


import com.example.demo.user.dto.UserAuthDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestBody UserAuthDto userAuthDto) {
        Optional<User> user = userService.authenticateUser(userAuthDto.getEmail(), userAuthDto.getPassword());

        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build()); // Retourne 401 si l'authentification échoue
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user)  {
        return userService.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check-email")
    public boolean checkEmail(@RequestParam String email) {
        return userService.emailExists(email);
    }
}

