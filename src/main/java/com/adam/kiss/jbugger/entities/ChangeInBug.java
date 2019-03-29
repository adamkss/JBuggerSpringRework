package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "changesInBugs")
@NoArgsConstructor
public class ChangeInBug {
    @Id
    @GeneratedValue
    private int id;

    private String changeText;

    private LocalDateTime timeOfChangeHappening;

    @ManyToOne
    private Bug bugChangeIsAssociatedWith;

    @ManyToOne
    private User changeAuthor;

    public ChangeInBug(String changeText, Bug bug, User changeAuthor) {
        this.changeText = changeText;
        this.timeOfChangeHappening = LocalDateTime.now();
        this.bugChangeIsAssociatedWith = bug;
        this.changeAuthor = changeAuthor;
    }
}
