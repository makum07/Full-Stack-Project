package com.skillup.taskmanager.service.impl;

import com.skillup.taskmanager.dto.UserDto;
import com.skillup.taskmanager.exception.UserAlreadyExistsException;
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
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistsException("User already exists with username: " + userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.getRoles().add(userRole);

        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getUsername(), null); // Don't return password
    }
}