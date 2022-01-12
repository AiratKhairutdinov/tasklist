package com.tasklist.backend.service;

import com.tasklist.backend.entity.Category;
import com.tasklist.backend.repo.CategoryRepository;
import com.tasklist.backend.search.CategorySearchValues;
import com.tasklist.backend.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategoryList() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    public Category getCategory(@PathVariable Long id) {
        return categoryRepository.findById(id).get();
    }

    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    public void updateCategory(@RequestBody Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }


    public List<Category>searchCategory(CategorySearchValues categorySearchValues) {
        return categoryRepository.findAllByTitle(categorySearchValues.getText());
    }


}
