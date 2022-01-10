package com.tasklist.backend.repo;

import com.tasklist.backend.entity.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatRepository extends CrudRepository<Stat, Long> {
}
