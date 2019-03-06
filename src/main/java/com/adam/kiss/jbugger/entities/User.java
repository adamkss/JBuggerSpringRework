package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "findAllUsers",
                query = "select e from User e"),
        @NamedQuery(name = "findUserByUserName",
                query = "select u from User u where u.username=:uname"),
        @NamedQuery(name = "getAllUsersNames",
                query = "select u.name from User u"),
        @NamedQuery(name = "getAllUsernames",
                query = "select u.username from User u"),
        @NamedQuery(name = "getUsersWithCertainRole",
                query = "SELECT u FROM User u WHERE :roleNeeded MEMBER  OF u.roles"),
        @NamedQuery(name = "getUsersWithSpecificRoles",
                query = "SELECT u FROM User u JOIN u.roles role WHERE role IN (:rolesAccepted)"),
        @NamedQuery(name = "getUsersWithPatternInUsername",
                query ="SELECT u.id FROM User u WHERE u.username LIKE :pattern")
})
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

    @ManyToMany
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @EqualsAndHashCode.Exclude
    private List<Role> roles = new ArrayList<Role>();

    private String username;

    @OneToMany(mappedBy = "assignedTo")
    @EqualsAndHashCode.Exclude
    private List<Bug> bugsAssignedToTheUser = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    @EqualsAndHashCode.Exclude
    private List<Comment> comments = new ArrayList<>();


    public User(String name, String phoneNumber, String email, List<Role> roles, String passwordHash) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.roles = roles;
        this.passwordHash = passwordHash;
    }

    @Override
    public String toString() {
        return username;
    }

    public String toUserDetailsString() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("Name: ").append(name).append("\n");
        toReturn.append("Phone Number: ").append(phoneNumber).append("\n");
        toReturn.append("Email: ").append(email).append("\n");
        toReturn.append("Roles: ");
        roles.parallelStream().forEach(role -> {
            toReturn.append(role.getRoleName()).append(" ");
        });
        toReturn.append("\n");
        return toReturn.toString();
    }
}
