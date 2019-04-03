package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    private String passwordHash;

    private boolean userActivated;

    @ManyToMany
    @OrderBy("created DESC")
    @EqualsAndHashCode.Exclude
    private List<Notification> notifications = new ArrayList<>();

    @ManyToOne
    private Role role;

    private String username;

    @OneToMany(mappedBy = "assignedTo")
    @EqualsAndHashCode.Exclude
    private List<Bug> bugsAssignedToTheUser = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "changeAuthor")
    @EqualsAndHashCode.Exclude
    private List<ChangeInBug> changesDoneInBugs = new ArrayList<>();

    public User(String name, String phoneNumber, String email, Role role, String passwordHash) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return username;
    }
}
