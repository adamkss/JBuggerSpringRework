package com.adam.kiss.jbugger.entities;

import com.adam.kiss.jbugger.enums.PredefinedStatusNames;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Status {
    public Status(Project project, String statusName) {
        this.project = project;
        this.statusName = statusName;
    }

    public Status(Project project, String statusName, String statusColor, int orderNr) {
        this.project = project;
        this.statusName = statusName;
        this.statusColor = statusColor;
        this.orderNr = orderNr;
    }

    public Status() {
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "statusName")
    private String statusName;

    private String statusColor;

    @OneToMany(mappedBy = "status", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JsonIgnore
    private List<Bug> bugsWithThisStatus;

    private int orderNr;

    @ManyToOne
    @JsonIgnore
    private Project project;

    public void incrementOrderNr() {
        this.orderNr++;
    }

    public void decrementOrderNr() {
        this.orderNr--;
    }

    public static class PredefinedStatuses {
        public static List<Status> PREDEFINED_STATUSES = new ArrayList<>();

        static {
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.NEW.toString(),
                    "#78909C",
                    0
            ));
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.INFO_NEEDED.toString(),
                    "#F57F17",
                    1
            ));
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.IN_PROGRESS.toString(),
                    "#4527A0",
                    2
            ));
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.FIXED.toString(),
                    "#4CAF50",
                    3
            ));
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.CLOSED.toString(),
                    "#795548",
                    4
            ));
            PREDEFINED_STATUSES.add(new Status(
                    null,
                    PredefinedStatusNames.REJECTED.toString(),
                    "#D32F2F",
                    5
            ));
        }
    }
}