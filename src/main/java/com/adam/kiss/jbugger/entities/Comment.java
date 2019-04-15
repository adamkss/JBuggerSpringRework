package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "comments")
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne
    private Bug bug;

    private String comment;

    @OneToMany(mappedBy = "parentComment")
    private List<PartialCommentUserMention> partialCommentUserMentions;

    @ManyToOne
    private User author;

    private LocalDateTime createdTime = LocalDateTime.now();
}
