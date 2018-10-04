package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BugRepository extends JpaRepository<Bug,Integer> {
}
