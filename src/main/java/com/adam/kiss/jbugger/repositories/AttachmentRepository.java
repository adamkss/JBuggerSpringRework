package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Integer> {
}
