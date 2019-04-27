package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    public boolean isUserAdmin(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .anyMatch(authority -> ((GrantedAuthority) authority).getAuthority().equalsIgnoreCase("ROLE_ADM"));
    }

    public boolean isUserPM(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities()
                .stream()
                .anyMatch(authority -> ((GrantedAuthority) authority).getAuthority().equalsIgnoreCase("ROLE_PM"));
    }
}
