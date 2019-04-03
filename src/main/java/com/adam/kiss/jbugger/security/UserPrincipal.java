package com.adam.kiss.jbugger.security;

import com.adam.kiss.jbugger.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private int id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserPrincipal create(User user){
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPasswordHash(),
                authorities
        );
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
