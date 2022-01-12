package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Priority;
import com.tasklist.backend.search.PrioritySearchValues;
import com.tasklist.backend.service.PriorityService;
import com.tasklist.backend.util.MyLogger;
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

    private PriorityService priorityService;

    @Autowired
    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }


    @GetMapping("all")
    public List<Priority> getPriorityList() {

        MyLogger.displayMethodName("PriorityController: getPriorityList()");

        return priorityService.getPriorityList();
    }

    @PostMapping("add")
    public ResponseEntity<Priority> addPriority(@RequestBody Priority priority) {

        MyLogger.displayMethodName("PriorityController: addPriority()");

        if (priority.getId() != null || priority.getId() != 0) {
            return new ResponseEntity("Redundant param: Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priorityService.addPriority(priority));
    }

    @GetMapping("{id}")
    public ResponseEntity<Priority> getPriority(@PathVariable Long id) {

        MyLogger.displayMethodName("PriorityController: getPriority()");

        Priority priority = null;

        try {
            priority = priorityService.getPriority(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no priority with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(priority);
    }

    @PutMapping("update")
    public ResponseEntity updatePriority(@RequestBody Priority priority) {

        MyLogger.displayMethodName("PriorityController: updatePriority()");

        if (priority.getId() == null || priority.getId() == 0) {
            return new ResponseEntity("Missed param: Id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getTitle() == null || priority.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        if (priority.getColor() == null || priority.getColor().trim().length() == 0) {
            return new ResponseEntity("Missed param: color", HttpStatus.NOT_ACCEPTABLE);
        }

        priorityService.updatePriority(priority);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deletePriority(@PathVariable Long id) {

        MyLogger.displayMethodName("PriorityController: deletePriority()");

        try {
            priorityService.deletePriority(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no category with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Priority>> searchPriority(@RequestBody PrioritySearchValues prioritySearchValues) {

        MyLogger.displayMethodName("PriorityController: search()");

        return ResponseEntity.ok(priorityService.searchPriority(prioritySearchValues));
    }
}

