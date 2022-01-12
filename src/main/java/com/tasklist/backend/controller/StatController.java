package com.tasklist.backend.controller;

import com.tasklist.backend.entity.Stat;
import com.tasklist.backend.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stat/")
public class StatController {

    private StatService statService;

    private final long defaultId = 1;

    @Autowired
    public StatController(StatService statService) {
        this.statService = statService;
    }

    @GetMapping()
    public ResponseEntity<Stat> getStat() {
        return ResponseEntity.ok(statService.getStat(defaultId));
    }

}
