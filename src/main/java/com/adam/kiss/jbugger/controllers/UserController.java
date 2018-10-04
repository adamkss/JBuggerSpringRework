package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewUserDtoOut;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<ViewUserDtoOut> getAllUser(){
        return ViewUserDtoOut.mapToDtoList(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ViewUserDtoOut> getUserWithId(@PathVariable("id")Integer id){
        Optional<User> user = userService.getUserById(id);
        if(user.isPresent())
            return ResponseEntity.ok(
                    ViewUserDtoOut.mapToDto(user.get())
            );
        else
            return ResponseEntity.notFound().build();
    }
}
