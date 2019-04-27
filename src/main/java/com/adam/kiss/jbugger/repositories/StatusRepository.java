package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {
    public Optional<Status> findByStatusNameAndProject(String statusName, Project project);

    public List<Status> findAllByProjectOrderByOrderNrAsc(Project project);

    public Status findByOrderNrAndProject(int orderNr, Project project);
}
