package com.skillup.taskmanager.service.custom;

import com.skillup.taskmanager.dto.TaskDto;
import com.skillup.taskmanager.service.base.BaseService;

import java.util.List;

public interface TaskService extends BaseService<TaskDto, Long> {
    List<TaskDto> getTasksByCategoryId(Long categoryId);
}
