package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Stat;
import com.tasklist.backend.repo.StatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stat/")
public class StatController {

    private StatRepository statRepository;

    private final long defaultId = 1;

    @Autowired
    public StatController(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @GetMapping()
    public ResponseEntity<Stat> getStat() {
        return ResponseEntity.ok(statRepository.findById(defaultId).get());
    }

}
