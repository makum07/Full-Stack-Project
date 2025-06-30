package com.skillup.taskmanager.service.impl;

import com.skillup.taskmanager.dto.TaskDto;
import com.skillup.taskmanager.mapper.TaskMapper;
import com.skillup.taskmanager.model.Task;
import com.skillup.taskmanager.model.User;
import com.skillup.taskmanager.repository.TaskRepository;
import com.skillup.taskmanager.repository.UserRepository;
import com.skillup.taskmanager.service.custom.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends DynamicServiceImpl<Task, TaskDto, Long> implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper mapper,
                           UserRepository userRepository) {
        super(taskRepository, mapper);
        this.taskRepository = taskRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<TaskDto> getTasksByCategoryId(Long categoryId) {
        User currentUser = getCurrentUser();
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        List<Task> tasks;

        if (isAdmin) {
            tasks = taskRepository.findByCategoryId(categoryId);
        } else {
            tasks = taskRepository.findByCategoryIdAndUserId(categoryId, currentUser.getId());
        }

        return tasks.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAll() {
        User currentUser = getCurrentUser();
        boolean isAdmin = currentUser.getRoles().stream()
                .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        List<Task> tasks;

        if (isAdmin) {
            tasks = taskRepository.findAll();
        } else {
            tasks = taskRepository.findByUserId(currentUser.getId());
        }

        return tasks.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto create(TaskDto dto) {
        Task task = mapper.toEntity(dto);

        // Attach logged-in user
        User currentUser = getCurrentUser();
        task.setUser(currentUser);

        Task savedTask = taskRepository.save(task);
        return mapper.toDto(savedTask);
    }


    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
