package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Category;
import com.tasklist.backend.entity.Priority;
import com.tasklist.backend.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/category/")
public class CategoryController {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("all")
    public List<Category> categoryList() {
        return categoryRepository.findAllByOrderByTitleAsc();
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {

        Category category = null;

        try {
            category = categoryRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping("add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        if (category.getId() != null || category.getId() != 0) {
            return new ResponseEntity("Redundant param: Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryRepository.save(category));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {

        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }
}
