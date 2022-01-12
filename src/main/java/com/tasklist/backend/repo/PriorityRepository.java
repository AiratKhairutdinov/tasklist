package com.tasklist.backend.repo;

import com.tasklist.backend.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    List<Priority> findAllByOrderByTitleAsc();

    @Query("select p from Priority p where " +
            "(:title is null or :title='' or lower(p.title) like lower(concat('%', :title, '%'))) " +
            "order by p.title asc")
    List<Priority> findAllByTitle(@Param("title") String title);
}
