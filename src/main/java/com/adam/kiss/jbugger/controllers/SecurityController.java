package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.security.JwtAuthenticationResponse;
import com.adam.kiss.jbugger.security.JwtTokenProvider;
import com.adam.kiss.jbugger.security.LoginRequest;
import com.adam.kiss.jbugger.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class SecurityController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();

        return ResponseEntity.ok(
                new JwtAuthenticationResponse(
                        jwt,
                        userPrincipal.getUsername(),
                        userPrincipal.getName(),
                        userPrincipal.isFirstTimeLogin()
                )
        );
    }
}
