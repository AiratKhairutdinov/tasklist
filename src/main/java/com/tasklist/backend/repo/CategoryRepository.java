package com.tasklist.backend.repo;

import com.tasklist.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByTitleAsc();

    @Query("select c from Category c where " +
            "(:title is null or :title='' or lower(c.title) like lower(concat('%', :title,'%'))) " +
            "order by c.title asc")
    List<Category> findAllByTitle(@Param("title") String title);
}
