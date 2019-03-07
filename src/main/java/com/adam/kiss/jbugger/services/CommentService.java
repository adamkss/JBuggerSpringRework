package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Comment;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // TODO :Add user here
    public Comment createComment(String commentMessage, Bug associatedBug, User author){
        Comment comment = new Comment();
        comment.setBug(associatedBug);
        comment.setCreatedTime(LocalDateTime.now());
        comment.setComment(commentMessage);
        comment.setAuthor(author);
        return commentRepository.save(comment);
    }
}
