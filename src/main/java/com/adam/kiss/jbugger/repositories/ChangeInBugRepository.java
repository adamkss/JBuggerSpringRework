package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.ChangeInBug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeInBugRepository extends JpaRepository<ChangeInBug, Integer> {
}
