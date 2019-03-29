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

    private boolean isFieldRelatedChange;

    private String fieldChanged;

    private String oldValue;

    private String newValue;

    public ChangeInBug(String changeText, Bug bug, User changeAuthor) {
        this.changeText = changeText;
        this.timeOfChangeHappening = LocalDateTime.now();
        this.bugChangeIsAssociatedWith = bug;
        this.changeAuthor = changeAuthor;
        this.isFieldRelatedChange = false;
    }

    public ChangeInBug(String changeText, Bug bug, User changeAuthor, String fieldChanged, String oldValue,
                       String newValue) {
        this.changeText = changeText;
        this.timeOfChangeHappening = LocalDateTime.now();
        this.bugChangeIsAssociatedWith = bug;
        this.changeAuthor = changeAuthor;
        this.fieldChanged = fieldChanged;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.isFieldRelatedChange = true;
    }
}
