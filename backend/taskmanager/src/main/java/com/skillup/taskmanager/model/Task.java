package com.skillup.taskmanager.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Boolean completed;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
