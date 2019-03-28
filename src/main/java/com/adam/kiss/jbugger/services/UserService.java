package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    public User getUserById(Integer id) throws UserIdNotValidException {
        return userRepository.findById(id).orElseThrow(UserIdNotValidException::new);
    }

    public List<UserWithNameAndUsernameProjection> getAllUsersWithNamesAndUsernames(){
        return userRepository.findAllUsersWithNamesAndUsernamesProjectedBy();
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public List<UserWithNameAndUsernameProjection> findAllByNameSearchString(String searchString){
        return userRepository.findAllByNameSearchString(searchString);
    }
}
