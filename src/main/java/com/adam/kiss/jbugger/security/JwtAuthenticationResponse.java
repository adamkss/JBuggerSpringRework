package com.adam.kiss.jbugger.security;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtAuthenticationResponse {
    private final String accessToken;
    private String tokenType = "Bearer";
    private final String username;
    private final String name;
    private final boolean isFirstTimeLogin;
}