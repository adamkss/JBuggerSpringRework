package com.adam.kiss.jbugger.repositories;

import com.adam.kiss.jbugger.entities.Notification;
import com.adam.kiss.jbugger.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllByOrderByCreatedDesc();
}
