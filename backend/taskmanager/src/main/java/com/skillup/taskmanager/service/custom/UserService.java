package com.skillup.taskmanager.service.custom;

import com.skillup.taskmanager.dto.UserDto;

public interface UserService {
    UserDto registerNewUser(UserDto userDto);
}
