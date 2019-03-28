package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewUserDtoOut;
import com.adam.kiss.jbugger.dtos.ViewUserShortDtoOut;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @GetMapping("/namesAndUsernames")
    public List<UserWithNameAndUsernameProjection> getAllUsernames() {
        return userService.getAllUsersWithNamesAndUsernames();
    }

    @GetMapping("/byFilterStringInName/{filterString}")
    public List<UserWithNameAndUsernameProjection> getAllUsernamesByFilterStringInName(@PathVariable("filterString") String filterString) {
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
}
