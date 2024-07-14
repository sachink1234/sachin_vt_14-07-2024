package com.example.assignment.entity.repository;

import com.example.assignment.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur JOIN UserRoleMap urm ON ur.id = urm.rid WHERE urm.uid = :userId")
    List<UserRole> findRolesByUserId(@Param("userId") Long userId);
}
