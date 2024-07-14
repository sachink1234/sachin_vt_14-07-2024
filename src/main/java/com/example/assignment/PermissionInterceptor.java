package com.example.assignment;


import com.example.assignment.entity.User;
import com.example.assignment.entity.UserRole;
import com.example.assignment.enums.LogicEnum;
import com.example.assignment.enums.Permission;
import com.example.assignment.enums.PermissionsEnum;
import com.example.assignment.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true; // Skip if not a method handler
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();

        if (method.isAnnotationPresent(Permission.class)) {
            Permission permission = method.getAnnotation(Permission.class);
            PermissionsEnum[] permissions = permission.permissions();
            LogicEnum logicType = permission.type();

            // Mock getting user ID from request, replace with actual implementation
            Long userId = 1L;
            boolean isAuthorized = checkAuthorization(userId, Arrays.asList(permissions), logicType);
            if (!isAuthorized) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        return true;
    }

    private boolean checkAuthorization(Long userId, List<PermissionsEnum> permissions, LogicEnum logicType) {
        // Implement logic to fetch user roles and permissions from the database
        User user = userService.getUserById(userId);
        if (user == null) {
            return false;
        }

        List<UserRole> userRoles = userService.getUserRoles(userId); // Implement this in UserService


        boolean hasPermission = false;
        if (logicType == LogicEnum.All) {
            hasPermission = userRoles.stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .collect(Collectors.toSet())
                    .containsAll(permissions);
        } else if (logicType == LogicEnum.Any) {
            hasPermission = userRoles.stream()
                    .flatMap(role -> role.getPermissions().stream())
                    .collect(Collectors.toSet())
                    .stream()
                    .anyMatch(permissions::contains);
        }

        return hasPermission;
    }
    }
