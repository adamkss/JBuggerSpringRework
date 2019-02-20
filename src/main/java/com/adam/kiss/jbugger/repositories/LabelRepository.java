package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelRepository extends JpaRepository<Label, Integer> {
    Optional<Label> findByLabelName(String labelName);
}
