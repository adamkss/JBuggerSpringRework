package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Integer> {
}
