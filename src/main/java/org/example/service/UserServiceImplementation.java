package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(Math.toIntExact(id));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(Math.toIntExact(id))) {
            userRepository.deleteById(Math.toIntExact(id));
            return true;
        } else {
            return false;
        }
    }
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(Math.toIntExact(id)).map(user -> {
            user.setName(userDetails.getName());
            return userRepository.save(user);
        }).orElse(null);
    }
}
