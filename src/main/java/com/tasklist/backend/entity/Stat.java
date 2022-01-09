package com.tasklist.backend.entity;

import lombok.Data;
import javax.persistence.*;


@Entity
@Data
public class Stat {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "completed_total")
    private Long completedTotal;

    @Column(name = "uncompleted_total")
    private Long uncompletedTotal;

}
