package com.example.demo.user.service;


import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> authenticateUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        // Si l'utilisateur existe et que le mot de passe correspond
        if (user != null && user.getPassword().equals(password)) {
            return Optional.of(user);
        }
        return Optional.empty(); // Si l'utilisateur ou le mot de passe est incorrect
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());
            existingUser.setPassword(user.getPassword());
            // Si tu veux aussi pouvoir modifier le rôle :
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }



    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}

