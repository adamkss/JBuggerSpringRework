package com.adam.kiss.jbugger.entities;

import com.adam.kiss.jbugger.enums.NotificationType;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "notifications")
public class Notification {
    @GeneratedValue
    @Id
    private int id;

    @Enumerated(value = EnumType.STRING)
    private NotificationType notificationType;

    @Lob
    private String text;

    @ManyToMany(mappedBy = "notifications")
    private List<User> userList = new ArrayList<>();

    boolean isRelatedToBug;

    @ManyToOne
    private Bug bug;

    private LocalDateTime created;

    public Notification(NotificationType notificationType, String text) {
        this.notificationType = notificationType;
        this.text = text;
        this.created = LocalDateTime.now();
    }

    public Notification(NotificationType notificationType, String text, LocalDateTime created) {
        this.notificationType = notificationType;
        this.text = text;
        this.created = created;
    }

    public Notification(NotificationType notificationType, String text, Bug bug) {
        this.notificationType = notificationType;
        this.text = text;
        this.bug = bug;
        this.created = LocalDateTime.now();
    }

    public Notification() {
        this.created = LocalDateTime.now();
    }
}
