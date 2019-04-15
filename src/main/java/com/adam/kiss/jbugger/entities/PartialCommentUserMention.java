package com.adam.kiss.jbugger.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
public class PartialCommentUserMention {
    @Id
    @GeneratedValue
    private int id;

    private String content;
    private boolean isUserRelevant;
    private Integer userId;
    private String username;
    private String usersName;

    @ManyToOne
    private Comment parentComment;

    public PartialCommentUserMention(String content) {
        this.content = content;
        this.isUserRelevant = false;
    }

    public PartialCommentUserMention(String content, Integer userId, String username, String userName) {
        this.content = content;
        this.userId = userId;
        this.username = username;
        this.usersName = userName;
        this.isUserRelevant = true;
    }
}
