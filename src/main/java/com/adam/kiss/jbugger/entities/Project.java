package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "projects")
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue
    private int id;

    private String name;

    @OneToMany(mappedBy = "project")
    @EqualsAndHashCode.Exclude
    private List<Bug> bugs;

    @ManyToMany (mappedBy = "projects")
    @EqualsAndHashCode.Exclude
    private List<User> usersOfProject = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    @EqualsAndHashCode.Exclude
    private List<Label> labelsOfProject = new ArrayList<>();

    @OneToMany(mappedBy = "project")
    @EqualsAndHashCode.Exclude
    private List<Status> projectStatuses = new ArrayList<>();

    public Project(String name) {
        this.name = name;
    }
}
