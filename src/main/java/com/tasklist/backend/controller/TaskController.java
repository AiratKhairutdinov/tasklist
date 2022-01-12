package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Task;
import com.tasklist.backend.repo.TaskRepository;
import com.tasklist.backend.search.TaskSearchValues;
import com.tasklist.backend.service.TaskService;
import com.tasklist.backend.util.MyLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("all")
    public List<Task> getTaskList() {

        MyLogger.displayMethodName("TaskController: getTaskList()");

        return taskService.getTaskList();
    }

    @GetMapping("{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {

        MyLogger.displayMethodName("TaskController: getTask()");

        Task task = null;

        try {
            task = taskService.getTask(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no task with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {

        MyLogger.displayMethodName("TaskController: deleteTask()");

        try {
            taskService.deleteTask(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("There is no task with id = " + id, HttpStatus.NOT_ACCEPTABLE);
        }

        return new ResponseEntity(HttpStatus.OK);

    }

    @PostMapping("add")
    public ResponseEntity<Task> addTask(@RequestBody Task task) {

        MyLogger.displayMethodName("TaskController: addTask()");

        if (task.getId() != null || task.getId() != 0) {
            return new ResponseEntity("Redundant param: Id must be null", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        return ResponseEntity.ok(taskService.addTask(task));
    }

    @PutMapping("update")
    public ResponseEntity updateTask(@RequestBody Task task) {

        MyLogger.displayMethodName("TaskController: updateTask()");

        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("Missed param: Id", HttpStatus.NOT_ACCEPTABLE);
        }

        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("Missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }

        taskService.updateTask(task);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Page<Task>> searchTask(@RequestBody TaskSearchValues taskSearchValues) {

        MyLogger.displayMethodName("TaskController: searchTask()");

        String title = taskSearchValues.getTitle() != null? taskSearchValues.getTitle() : null;
        Integer completed = taskSearchValues.getCompleted() != null? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null? taskSearchValues.getCategoryId() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null? taskSearchValues.getPageSize() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getDirection() != null? taskSearchValues.getDirection() : null;

        Sort.Direction direction = sortDirection ==null ||
                sortDirection.trim().equals("asc") ||
                sortDirection.trim().length() == 0 ? Sort.Direction.ASC : Sort.Direction.DESC;

        Sort sort = Sort.by(direction, sortColumn);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page result = taskService.searchTask(title, completed, priorityId, categoryId, pageRequest);

        return ResponseEntity.ok(result);
    }


}
