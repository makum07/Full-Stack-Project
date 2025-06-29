package com.skillup.taskmanager.service.impl;

import com.skillup.taskmanager.dto.CategoryDto;
import com.skillup.taskmanager.mapper.CategoryMapper;
import com.skillup.taskmanager.model.Category;
import com.skillup.taskmanager.repository.CategoryRepository;
import com.skillup.taskmanager.service.custom.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends DynamicServiceImpl<Category, CategoryDto, Long> implements CategoryService {

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper mapper) {
        super(repository, mapper);
    }
}
