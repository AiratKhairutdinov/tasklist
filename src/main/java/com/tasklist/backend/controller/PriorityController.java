package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Category;
import com.tasklist.backend.entity.Priority;
import com.tasklist.backend.repo.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/priority/")
public class PriorityController {

    private PriorityRepository priorityRepository;

    @Autowired
    public PriorityController(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    @GetMapping("all")
    public List<Priority> priorityList() {
        return priorityRepository.findAllByOrderByTitleAsc();
    }

    @PostMapping("add")
    public ResponseEntity<Priority> addPriority(@RequestBody Priority priority) {

        if (priority.getId() != null || priority.getId() != 0) {
            return new ResponseEntity("Redundant param: Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityRepository.save(priority));
    }

    @GetMapping("{id}")
    public ResponseEntity<Priority> getPriority(@PathVariable Long id) {

        Priority priority = null;

        try {
            priority = priorityRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no priority with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @PutMapping("update")
    public ResponseEntity<Priority> updatePriority(@RequestBody Priority priority) {

        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("Missed param: Id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityRepository.save(priority));

    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Priority> deletePriority(@PathVariable Long id) {
        try {
            priorityRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

}

