package com.example.assignment.entity.repository;

import com.example.assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
