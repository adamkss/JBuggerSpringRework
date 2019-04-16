package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Comment;
import com.adam.kiss.jbugger.entities.PartialCommentUserMention;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.repositories.CommentRepository;
import com.adam.kiss.jbugger.repositories.PartialCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PartialCommentRepository partialCommentRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    // TODO :Add user here
    public Comment createComment(String commentMessage, Bug associatedBug, User author) {
        Comment comment = new Comment();
        comment.setBug(associatedBug);
        comment.setCreatedTime(LocalDateTime.now());
        comment.setComment(commentMessage);
        comment.setAuthor(author);
        commentRepository.save(comment);

        List<PartialCommentUserMention> partialCommentUserMentions = getCommentParts(commentMessage);
        partialCommentUserMentions.forEach(commentPart -> {
            commentPart.setParentComment(comment);
        });
        partialCommentRepository.saveAll(partialCommentUserMentions);

        comment.setPartialCommentUserMentions(partialCommentUserMentions);
        commentRepository.save(comment);

        partialCommentUserMentions
                .stream()
                .filter(partialCommentUserMention -> partialCommentUserMention.isUserRelevant())
                .forEach(partialCommentUserMention -> {
                    try {
                        notificationService.sendNotificationUserWasMentioned(
                                userService.getUserById(partialCommentUserMention.getUserId()),
                                associatedBug
                        );
                    } catch (UserIdNotValidException e) {
                    }
                });
        return comment;
    }

    private List<PartialCommentUserMention> getCommentParts(String comment) {
        List<PartialCommentUserMention> partialCommentUserMentions = new ArrayList<>();

        int beginIndex = 0;
        int endIndex = 0;
        int commentLength = comment.length();

        while (endIndex < commentLength) {
            if (comment.charAt(endIndex) != '@') {
                endIndex++;
            } else {
                if (beginIndex != endIndex) {
                    partialCommentUserMentions.add(new PartialCommentUserMention(
                            comment.substring(beginIndex, endIndex)
                    ));
                    beginIndex = endIndex;
                }
                endIndex++;
                while (endIndex < commentLength) {
                    char currentChar = comment.charAt(endIndex);
                    //if it`s present
                    if ("-.$%^&/+-*,@(\n ".indexOf(currentChar) >= 0) {
                        break;
                    }
                    endIndex++;
                }

                String usernameWithAt = comment.substring(beginIndex, endIndex);
                User mentionedUser = userService.getUserByUsername(usernameWithAt.substring(1));
                if (mentionedUser != null) {
                    partialCommentUserMentions.add(new PartialCommentUserMention(
                            usernameWithAt,
                            mentionedUser.getId(),
                            mentionedUser.getUsername(),
                            mentionedUser.getName()
                    ));
                } else {
                    partialCommentUserMentions.add(new PartialCommentUserMention(
                            usernameWithAt
                    ));
                }

                beginIndex = endIndex;
            }
        }
        if (beginIndex != endIndex) {
            partialCommentUserMentions.add(new PartialCommentUserMention(
                    comment.substring(beginIndex, endIndex)
            ));
        }

        return partialCommentUserMentions;
    }
}
