package com.skillup.taskmanager.mapper;

import com.skillup.taskmanager.dto.TaskDto;
import com.skillup.taskmanager.model.Category;
import com.skillup.taskmanager.model.Task;
import com.skillup.taskmanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper implements Mapper<Task, TaskDto> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public TaskDto toDto(Task entity) {
        return new TaskDto(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCompleted(),
                entity.getDueDate(),
                entity.getCategory() != null ? entity.getCategory().getId() : null
        );
    }

//    @Override
//    public Task toEntity(TaskDto dto) {
//        Category category = categoryRepository.findById(dto.getCategoryId())
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        return new Task(
//                dto.getId(),
//                dto.getTitle(),
//                dto.getDescription(),
//                dto.getCompleted(),
//                dto.getDueDate(),
//                category
//        );
//    }

    @Override
    public Task toEntity(TaskDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Task task = new Task();
        task.setId(dto.getId());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setDueDate(dto.getDueDate());
        task.setCategory(category);

        // 🔴 Don't set User here, it will be set in the service layer from authenticated user

        return task;
    }

}
