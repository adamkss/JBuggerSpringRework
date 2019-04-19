package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.*;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.RoleNotFoundException;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.NotificationService;
import com.adam.kiss.jbugger.services.ProjectService;
import com.adam.kiss.jbugger.services.RoleService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/projects")
@CrossOrigin
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final UserService userService;

    @GetMapping
    public List<ViewProjectShortOutDto> getAllProjects() {
        return ViewProjectShortOutDto.mapToDtoList(projectService.getAllProjects());
    }

    @GetMapping("/currentUser")
    public List<ViewProjectShortOutDto> getAllProjectsOfCurrentUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws UserIdNotValidException {
        User user = userService.getUserById(userPrincipal.getId());
        return ViewProjectShortOutDto.mapToDtoList(user.getProjects());
    }

}