package com.skillup.taskmanager.service.impl;

import com.skillup.taskmanager.dto.TaskDto;
import com.skillup.taskmanager.mapper.TaskMapper;
import com.skillup.taskmanager.model.Task;
import com.skillup.taskmanager.repository.TaskRepository;
import com.skillup.taskmanager.service.custom.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl extends DynamicServiceImpl<Task, TaskDto, Long> implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;

    public TaskServiceImpl(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
        this.taskRepository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<TaskDto> getTasksByCategoryId(Long categoryId) {
        return taskRepository.findByCategoryId(categoryId)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
