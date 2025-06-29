package com.skillup.taskmanager.controller;

import com.skillup.taskmanager.dto.TaskDto;
import com.skillup.taskmanager.service.custom.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @PostMapping
    public TaskDto create(@RequestBody TaskDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<TaskDto> getAll(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        if (categoryId != null) {
            return service.getTasksByCategoryId(categoryId);
        } else {
            return service.getAll();
        }
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public TaskDto update(@PathVariable Long id, @RequestBody TaskDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
