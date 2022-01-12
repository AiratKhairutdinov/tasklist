package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Category;
import com.tasklist.backend.search.CategorySearchValues;
import com.tasklist.backend.service.CategoryService;
import com.tasklist.backend.util.MyLogger;
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

    private CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("all")
    public List<Category> getCategoryList() {

        MyLogger.displayMethodName("CategoryController: getPriorityList()");

        return categoryService.getCategoryList();
    }

    @GetMapping("{id}")
    public ResponseEntity<Category> getCategory(@PathVariable Long id) {

        MyLogger.displayMethodName("CategoryController: getCategory()");

        Category category = null;

        try {
            category = categoryService.getCategory(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(category);
    }

    @PostMapping("add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {

        MyLogger.displayMethodName("CategoryController: addCategory()");

        if (category.getId() != null || category.getId() != 0) {
            return new ResponseEntity("Redundant param: Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(categoryService.addCategory(category));
    }

    @PutMapping("update")
    public ResponseEntity updateCategory(@RequestBody Category category) {

        MyLogger.displayMethodName("CategoryController: updateCategory()");

        if (category.getId() == null || category.getId() == 0) {
            return new ResponseEntity("Missed param: Id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (category.getTitle() == null || category.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        categoryService.updateCategory(category);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Long id) {

        MyLogger.displayMethodName("CategoryController: deleteCategory()");

        try {
            categoryService.deleteCategory(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Category>> searchCategory(@RequestBody CategorySearchValues categorySearchValues) {

        MyLogger.displayMethodName("CategoryController: search()");

        return ResponseEntity.ok(categoryService.searchCategory(categorySearchValues));
    }
}
