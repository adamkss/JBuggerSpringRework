package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Role;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import utils.UserHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) throws UserIdNotValidException {
        return userRepository.findById(id).orElseThrow(UserIdNotValidException::new);
    }

    public List<UserWithNameAndUsernameProjection> getAllUsersWithNamesAndUsernames() {
        return userRepository.findAllUsersWithNamesAndUsernamesProjectedBy();
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserWithNameAndUsernameProjection> findAllByNameSearchString(String searchString) {
        return userRepository.findAllByNameSearchString(searchString);
    }

    public String getUsernameForUser(String firstName, String lastName) throws UserNotValidException {
        int numberOfTry = 5;

        while (numberOfTry > 0) {
            String username = UserHelper.generateUserName(firstName, lastName, numberOfTry--);
            if (getUserByUsername(username) == null) {
                return username;
            }
        }
        return null;
    }

    public User createUser(String name, String phoneNumber, String email, String password, Role role)
            throws UserNotValidException {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setEmail(email);
        newUser.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        newUser.setRole(role);
        newUser.setUserActivated(true);

        String[] userNameParts = name.split(" ");

        newUser.setUsername(getUsernameForUser(userNameParts[0], userNameParts[1]));

        return userRepository.save(newUser);
    }
}
