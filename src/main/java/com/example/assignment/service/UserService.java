package com.example.assignment.service;

import com.example.assignment.entity.User;
import com.example.assignment.entity.UserRole;
import com.example.assignment.entity.repository.PerRoleMapRepository;
import com.example.assignment.entity.repository.UserRepository;
import com.example.assignment.entity.repository.UserRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PerRoleMapRepository perRoleMapRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<UserRole> getUserRoles(Long userId) {

        return userRoleRepository.findRolesByUserId(userId);
    }

}
