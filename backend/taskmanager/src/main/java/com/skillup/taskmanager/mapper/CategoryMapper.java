package com.skillup.taskmanager.mapper;

import com.skillup.taskmanager.dto.CategoryDto;
import com.skillup.taskmanager.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements Mapper<Category, CategoryDto> {

    @Override
    public CategoryDto toDto(Category entity) {
        return new CategoryDto(entity.getId(), entity.getName());
    }

    @Override
    public Category toEntity(CategoryDto dto) {
        return new Category(dto.getId(), dto.getName());
    }
}
