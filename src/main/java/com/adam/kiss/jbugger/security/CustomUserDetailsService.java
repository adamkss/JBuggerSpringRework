package com.adam.kiss.jbugger.security;

import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null ){
            throw new UsernameNotFoundException(username + " does not exist!");
        }

        return UserPrincipal.create(user);
    }

    public UserDetails loadUserById(int id){
        User user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Username with id " +
                id + " doesn't exist!"));
        return UserPrincipal.create(user);
    }
}
