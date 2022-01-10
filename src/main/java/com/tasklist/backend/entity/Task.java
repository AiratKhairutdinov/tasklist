package com.tasklist.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "completed")
    private Integer completed;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

}
