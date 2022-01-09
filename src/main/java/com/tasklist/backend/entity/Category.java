package com.tasklist.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Category {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed_count")
    private Long completedCount;

    @Column(name = "uncompleted_count")
    private Long uncompletedCount;

}
