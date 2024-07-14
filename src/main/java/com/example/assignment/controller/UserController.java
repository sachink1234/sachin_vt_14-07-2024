package com.example.assignment.controller;


import com.example.assignment.entity.User;
import com.example.assignment.enums.LogicEnum;
import com.example.assignment.enums.Permission;
import com.example.assignment.enums.PermissionsEnum;
import com.example.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Permission(
            permissions = {PermissionsEnum.AllowRead},
            type = LogicEnum.All
    )
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Permission(
            permissions = {PermissionsEnum.AllowRead},
            type = LogicEnum.All
    )
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Permission(
            permissions = {PermissionsEnum.AllowWrite},
            type = LogicEnum.All
    )
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @Permission(
            permissions = {PermissionsEnum.AllowWrite},
            type = LogicEnum.All
    )
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
