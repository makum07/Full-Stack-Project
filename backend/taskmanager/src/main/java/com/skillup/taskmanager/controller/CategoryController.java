package com.skillup.taskmanager.controller;

import com.skillup.taskmanager.dto.CategoryDto;
import com.skillup.taskmanager.service.custom.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin
public class CategoryController {

    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto) {
        return service.create(dto);
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PutMapping("/{id}")
    public CategoryDto update(@PathVariable Long id, @RequestBody CategoryDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
