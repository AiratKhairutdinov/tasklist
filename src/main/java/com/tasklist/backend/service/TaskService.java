package com.tasklist.backend.service;

import com.tasklist.backend.entity.Category;
import com.tasklist.backend.entity.Task;
import com.tasklist.backend.repo.TaskRepository;
import com.tasklist.backend.search.CategorySearchValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTaskList() {
        return taskRepository.findAll();
    }

    public Task getTask(@PathVariable Long id) {
        return taskRepository.findById(id).get();
    }

    public Task addTask(@RequestBody Task task) {
        return taskRepository.save(task);
    }

    public void updateTask(@RequestBody Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


    public Page<Task> searchTask(String title, Integer completed, Long priorityId, Long categoryId, PageRequest pageRequest) {
        return taskRepository.findAllByParams(title, completed, priorityId, categoryId, pageRequest);
    }
}
