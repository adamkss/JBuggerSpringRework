package com.adam.kiss.jbugger.entities;


import com.adam.kiss.jbugger.enums.Severity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "bugs")
@NoArgsConstructor
public class Bug {
    public static final Bug DUMMY_BUG = new Bug();
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String description;

    @Column(nullable = false)
    private String revision;

    private String fixedInRevision;

    private LocalDate targetDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Severity severity;

    @JoinColumn(nullable = false)
    @ManyToOne
    private User createdBy;

    @ManyToOne
    private Status status;

    @ManyToOne
    private User assignedTo;

    @OneToMany(mappedBy = "bug", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "bug", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @OrderBy("createdTime DESC")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @EqualsAndHashCode.Exclude
    private List<Label> labels = new ArrayList<>();

    @OneToMany(mappedBy ="bugChangeIsAssociatedWith")
    @OrderBy("timeOfChangeHappening DESC")
    private List<ChangeInBug> changesOfBug = new ArrayList<>();

    public Bug(String title, String description, String revision, String fixedInRevision, LocalDate targetDate, Severity severity, User createdBy, Status status, User assignedTo) {
        this.title = title;
        this.description = description;
        this.revision = revision;
        this.fixedInRevision = fixedInRevision;
        this.targetDate = targetDate;
        this.severity = severity;
        this.createdBy = createdBy;
        this.status = status;
        this.assignedTo = assignedTo;
    }

    public String toBugDetailsString() {
        StringBuilder detailsBuilder = new StringBuilder();
        detailsBuilder.append("Id: ").append(id).append("\n");
        detailsBuilder.append("Title: ").append(title).append("\n");
        detailsBuilder.append("Description: ").append(description).append("\n");
        detailsBuilder.append("Revision: ").append(revision).append("\n");
        detailsBuilder.append("Fixed in revision: ").append(fixedInRevision).append("\n");
        detailsBuilder.append("Target date: ").append(targetDate).append("\n");
        detailsBuilder.append("Severity: ").append(severity).append("\n");
        detailsBuilder.append("Created by: ").append(createdBy.getName()).append("\n");
        detailsBuilder.append("PredefinedStatusNames: ").append(status).append("\n");
        detailsBuilder.append("Assigned  to: ").append(assignedTo.getName()).append("\n");
        return detailsBuilder.toString();
    }
}
