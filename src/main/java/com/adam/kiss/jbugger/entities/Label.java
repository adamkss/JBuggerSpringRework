package com.adam.kiss.jbugger.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "labels")
@NoArgsConstructor
@AllArgsConstructor
public class Label {
    @Id
    @GeneratedValue
    private int id;

    private String labelName;

    private String backgroundColor;

    @ManyToMany(mappedBy = "labels")
    private List<Bug> bugsWithThisLabel;

    @ManyToOne
    private Project project;

    public Label(Project project, String labelName, String backgroundColor) {
        this.project = project;
        this.labelName = labelName;
        this.backgroundColor = backgroundColor;
    }
}
