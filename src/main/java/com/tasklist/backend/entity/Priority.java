package com.tasklist.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Priority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 45)
    private String title;

    @Column(name = "color", nullable = false, length = 45)
    private String color;


}
