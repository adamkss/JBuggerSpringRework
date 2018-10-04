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
@Table(name = "rights")
@NamedQueries({
        @NamedQuery(name = "findRightByName",
                query = "select r from Right r where r.denumire=:name"),
        @NamedQuery(name = "getAllRights",
                query = "select r from Right r")
})
public class Right {
    public static final Right PERMISSION_MANAGEMENT = new Right("PERMISSION_MANAGEMENT");
    public static final Right USER_MANAGEMENT = new Right("USER_MANAGEMENT");
    public static final Right BUG_MANAGEMENT = new Right("BUG_MANAGEMENT");
    public static final Right BUG_CLOSE = new Right("BUG_CLOSE");
    public static final Right BUG_EXPORT_PDF = new Right("BUG_EXPORT_PDF");
    public static final Map<String, Right> rightByName;

    static {
        rightByName = new HashMap<>();
        rightByName.put(PERMISSION_MANAGEMENT.getDenumire(), PERMISSION_MANAGEMENT);
        rightByName.put(USER_MANAGEMENT.getDenumire(), USER_MANAGEMENT);
        rightByName.put(BUG_MANAGEMENT.getDenumire(), BUG_MANAGEMENT);
        rightByName.put(BUG_CLOSE.getDenumire(), BUG_CLOSE);
        rightByName.put(BUG_EXPORT_PDF.getDenumire(), BUG_EXPORT_PDF);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;

    private String denumire;

    public Right(String denumire) {
        this.denumire = denumire;
    }

    @ManyToMany(mappedBy = "rights")
    @EqualsAndHashCode.Exclude
    private List<Role> rolesWithRight = new ArrayList<>();

    public Right() {
    }

    @Override
    public String toString() {
        return denumire;
    }

}
