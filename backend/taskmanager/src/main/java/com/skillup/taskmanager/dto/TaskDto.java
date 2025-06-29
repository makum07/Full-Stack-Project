package com.skillup.taskmanager.dto;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDate dueDate;
    private Long categoryId;
}
