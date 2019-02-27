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
    public Status(String statusName) {
        this.statusName = statusName;
    }

    public Status(String statusName, String statusColor) {
        this.statusName = statusName;
        this.statusColor = statusColor;
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

    public static class PredefinedStatuses {
        public static List<Status> PREDEFINED_STATUSES = new ArrayList<>();

        static {
            for (PredefinedStatusNames predefinedStatusName :
                    PredefinedStatusNames.values()) {
                PREDEFINED_STATUSES.add(new Status(predefinedStatusName.toString()));
            }
        }
    }
}