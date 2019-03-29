package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateCommentDtoIn;
import com.adam.kiss.jbugger.dtos.ViewCommentDtoOut;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.BugService;
import com.adam.kiss.jbugger.services.ChangeInBugService;
import com.adam.kiss.jbugger.services.CommentService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.View;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/comments")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BugService bugService;
    private final UserService userService;
    private final ChangeInBugService changeInBugService;

    @GetMapping("/bug/{bugId}")
    public List<ViewCommentDtoOut> getAllCommentsForBug(@PathVariable Integer bugId) throws BugNotFoundException {
        return bugService.getBugById(bugId)
                .getComments()
                .stream()
                .map(ViewCommentDtoOut::mapCommentToViewCommentOutDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/bug/{bugId}")
    public ViewCommentDtoOut createComment(@PathVariable Integer bugId,
                                           @RequestBody CreateCommentDtoIn createCommentDtoIn,
                                           @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws BugNotFoundException, UserIdNotValidException {
        User author = userService.getUserById(userPrincipal.getId());

        Bug associatedBug = bugService.getBugById(bugId);
        ViewCommentDtoOut viewCommentDtoOut = ViewCommentDtoOut.mapCommentToViewCommentOutDto(
                commentService.createComment(
                        createCommentDtoIn.getCommentText(),
                        associatedBug,
                        author
                )
        );

        changeInBugService.createChangeInBug(
                "User with username: \""
                        + userPrincipal.getUsername() + "\" added a comment.",
                associatedBug,
                author
        );

        return viewCommentDtoOut;
    }
}
