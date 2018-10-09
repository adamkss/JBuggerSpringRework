package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }

    public List<UserWithNameAndUsernameProjection> getAllUsersWithNamesAndUsernames(){
        return userRepository.findAllUsersWithNamesAndUsernamesProjectedBy();
    }
}
