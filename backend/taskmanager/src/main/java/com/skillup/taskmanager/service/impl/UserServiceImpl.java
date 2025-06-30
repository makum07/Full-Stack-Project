// 📁 service/impl/UserServiceImpl.java
package com.skillup.taskmanager.service.impl;

import com.skillup.taskmanager.dto.UserDto;
import com.skillup.taskmanager.model.Role;
import com.skillup.taskmanager.model.User;
import com.skillup.taskmanager.repository.RoleRepository;
import com.skillup.taskmanager.repository.UserRepository;
import com.skillup.taskmanager.service.custom.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        // Check if username exists using the new method
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        // Assign default role
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.getRoles().add(userRole);

        userRepository.save(user);
        return userDto;
    }
}