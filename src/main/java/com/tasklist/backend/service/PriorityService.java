package com.tasklist.backend.service;

import com.tasklist.backend.entity.Priority;
import com.tasklist.backend.repo.PriorityRepository;
import com.tasklist.backend.search.PrioritySearchValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class PriorityService {

    private final PriorityRepository priorityRepository;

    @Autowired
    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    public List<Priority> getPriorityList() {
        return priorityRepository.findAllByOrderByTitleAsc();
    }

    public Priority getPriority(@PathVariable Long id) {
        return priorityRepository.findById(id).get();
    }

    public Priority addPriority(@RequestBody Priority priority) {
        return priorityRepository.save(priority);
    }

    public void updatePriority(@RequestBody Priority priority) {
        priorityRepository.save(priority);
    }

    public void deletePriority(Long id) {
        priorityRepository.deleteById(id);
    }


    public List<Priority>searchPriority(PrioritySearchValues prioritySearchValues) {
        return priorityRepository.findAllByTitle(prioritySearchValues.getText());
    }
}
