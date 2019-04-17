package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.*;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.RoleNotFoundException;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.security.UserPrincipal;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public List<ViewUserDtoOut> getAllUser() {
        return ViewUserDtoOut.mapToDtoList(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ViewUserDtoOut> getUserWithId(@PathVariable("id") Integer id) throws UserIdNotValidException {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(
                ViewUserDtoOut.mapToDto(user)
        );
    }

    @GetMapping("/adminInfo")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public List<ViewUserForAdminDtoOut> getAllUsersForProjectPage() {
        return ViewUserForAdminDtoOut.mapToDtoList(userService.getAllUsers());
    }

    @GetMapping("/namesAndUsernames")
    public List<UserWithNameAndUsernameProjection> getAllUsernames() {
        return userService.getAllUsersWithNamesAndUsernames();
    }

    @GetMapping("/byFilterStringInName/{filterString}")
    public List<UserWithNameAndUsernameProjection> getAllUsernamesByFilterStringInName(
            @PathVariable("filterString") String filterString) {
        return userService.findAllByNameSearchString(filterString);
    }

    @GetMapping("/user/current")
    public ViewUserShortDtoOut getCurrentlyLoggedInUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws UserIdNotValidException {
        return ViewUserShortDtoOut.mapToDto(
                userService.getUserById(
                        userPrincipal.getId()
                )
        );
    }

    @PostMapping()
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public ResponseEntity<ViewUserForAdminDtoOut> createUser(@RequestBody CreateUserDtoIn createUserDtoIn)
            throws RoleNotFoundException, URISyntaxException, UserNotValidException {
        User createdUser = userService.createUser(
                createUserDtoIn.getName(),
                createUserDtoIn.getPhoneNumber(),
                createUserDtoIn.getEmail(),
                createUserDtoIn.getPassword(),
                roleService.findByRoleName(createUserDtoIn.getRole())
        );

        return ResponseEntity
                .created(
                        new URI("/users/" + createdUser.getId())
                )
                .body(ViewUserForAdminDtoOut.mapToDto(createdUser));
    }

    @GetMapping("/current/notifications")
    public List<ViewNotificationDtoOut> getAllNotificationsOfUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) throws UserIdNotValidException {
        return ViewNotificationDtoOut.mapNotificationsListToDTOList(
                    userService.getAllNotificationsOfUser(userPrincipal.getId())
        );
    }

    @GetMapping("/current/notifications/count")
    public Integer getNumberOfNotificationsOfUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal) throws UserIdNotValidException {
        return userService.getNumberOfNotificationsOfUser(userPrincipal.getId());
    }
}