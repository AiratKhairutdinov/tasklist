package com.tasklist.backend.repo;

import com.tasklist.backend.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    List<Priority> findAllByOrderByTitleAsc();
}
