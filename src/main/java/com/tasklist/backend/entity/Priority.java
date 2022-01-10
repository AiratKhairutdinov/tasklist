package com.tasklist.backend.entity;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
public class Priority {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "color")
    private String color;


}
