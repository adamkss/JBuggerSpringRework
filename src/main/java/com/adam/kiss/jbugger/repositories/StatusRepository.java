package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    public Optional<Status> findByStatusName(String statusName);
}
