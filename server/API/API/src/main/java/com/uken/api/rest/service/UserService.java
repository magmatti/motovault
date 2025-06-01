package com.uken.api.rest.service;

import com.uken.api.entity.User;
import com.uken.api.rest.repostitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UsersRepository usersRepository;

    public User saveUser(User user) {
        return usersRepository.save(user);
    }

    public List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<User> getUserById(long id) {
        return usersRepository.findById(id);
    }

    public void deleteUserById(long id) {
        usersRepository.deleteById(id);
    }

    public User updateUser(long id, User updatedUser) {
        return usersRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            return usersRepository.save(user);
        }).orElseThrow(() -> new IllegalArgumentException("User with ID " + id + " not found"));
    }
}
