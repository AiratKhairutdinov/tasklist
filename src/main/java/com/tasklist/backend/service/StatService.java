package com.tasklist.backend.service;

import com.tasklist.backend.entity.Stat;
import com.tasklist.backend.repo.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatService {

    private final StatRepository statRepository;

    @Autowired
    public StatService(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    public Stat getStat(Long id) {
        return statRepository.findById(id).get();
    }

}
