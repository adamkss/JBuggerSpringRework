package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.PartialCommentUserMention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartialCommentRepository extends JpaRepository<PartialCommentUserMention,Integer> {
}
