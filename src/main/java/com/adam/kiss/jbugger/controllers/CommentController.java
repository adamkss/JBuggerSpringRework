package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewCommentDtoOut;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.services.BugService;
import com.adam.kiss.jbugger.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/comments")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final BugService bugService;

    @GetMapping("/bug/{bugId}")
    public List<ViewCommentDtoOut> getAllCommentsForBug(@PathVariable Integer bugId) throws BugNotFoundException {
        return bugService.getBugById(bugId)
                .getComments()
                .stream()
                .map(ViewCommentDtoOut::mapCommentToViewCommentOutDto)
                .collect(Collectors.toList());
    }
}
