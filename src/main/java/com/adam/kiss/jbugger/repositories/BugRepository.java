package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BugRepository extends JpaRepository<Bug,Integer> {

    @Query(
            "SELECT b FROM Bug b WHERE b.title LIKE CONCAT('%',:filter,'%') " +
                    "OR b.description LIKE CONCAT('%',:filter,'%') " +
                    "OR b.createdBy.name LIKE CONCAT('%',:filter,'%')"
    )
    public List<Bug> findAllFiltered(@Param("filter") String filter);
}
