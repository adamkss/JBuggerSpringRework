package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightRepository extends JpaRepository<Right,Integer> {
}
