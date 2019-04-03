package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "roles")
@NamedQueries({
        @NamedQuery(name = "findRoleByName",
                query = "select r from Role r where r.roleName=:name"),
        @NamedQuery(name = "findAllRoles",
                query = "select r from Role r"),
        @NamedQuery(name = "getAllRolesWithRight",
                query = "select r from Role r WHERE :rightNeeded MEMBER  OF r.rights")
})
public class Role {
    public static final Role ADM_ROLE = new Role("ROLE_ADM");
    public static final Role TEST_ROLE = new Role("ROLE_TEST");
    public static final Role PM_ROLE = new Role("ROLE_PM");
    public static final Role TM_ROLE = new Role("ROLE_TM");
    public static final Role DEV_ROLE = new Role("ROLE_DEV");
    public static final String[] ROLES_NAMES = new String[]{ADM_ROLE.roleName,TEST_ROLE.roleName,PM_ROLE.roleName,TM_ROLE.roleName,DEV_ROLE.roleName};
    public static final Map<String,Role> roleByName;
    static{
        roleByName = new HashMap<>();
        roleByName.put("ADM",ADM_ROLE);
        roleByName.put("TEST",TEST_ROLE);
        roleByName.put("PM",PM_ROLE);
        roleByName.put("TM",TM_ROLE);
        roleByName.put("DEV",DEV_ROLE);
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<User>();

    @ManyToMany()
    @JoinTable(name = "roles_right",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "right_id")
    )
    private List<Right> rights = new ArrayList<>();

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
