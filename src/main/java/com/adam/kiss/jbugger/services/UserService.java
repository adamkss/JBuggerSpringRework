package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Notification;
import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.Role;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import utils.UserHelper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) throws UserIdNotValidException {
        return userRepository.findById(id).orElseThrow(UserIdNotValidException::new);
    }

    public List<UserWithNameAndUsernameProjection> getAllUsersWithNamesAndUsernames(Project project) {
        return userRepository.findByProjectsContains(project);
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

    public String generateRandomSpecialCharacters() {
        RandomStringGenerator pwdGenerator = new RandomStringGenerator.Builder().withinRange(33, 45)
                .build();
        return pwdGenerator.generate(5);
    }

    public User createUser(String name, String phoneNumber, String email, Role role)
            throws UserNotValidException {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setEmail(email);

        String temporaryPassword = generateRandomSpecialCharacters();
        newUser.setPasswordHash(BCrypt.hashpw(temporaryPassword, BCrypt.gensalt()));

        newUser.setRole(role);
        newUser.setUserActivated(true);

        String[] userNameParts = name.split(" ");

        newUser.setUsername(getUsernameForUser(userNameParts[0], userNameParts[1]));

        newUser.setFirstTimeLogin(true);
        newUser = userRepository.save(newUser);

        emailService.sendPasswordToUser(
                email,
                newUser.getUsername(),
                temporaryPassword
        );
        return newUser;
    }

    public void addNotificationToUser(User user, Notification notification) {
        user.getNotifications().add(notification);
        userRepository.save(user);
    }

    public void addNotificationToUsers(List<User> users, Notification notification) {
        users
                .stream()
                .forEach(user -> addNotificationToUser(user, notification));
    }

    public void addProjectToUser(User user, Project project) {
        user.getProjects().add(project);
        userRepository.save(user);
    }

    public void removeProjectFromUser(User user, Project project) {
        user.getProjects().remove(project);
        userRepository.save(user);
    }

    public User changePasswordOfUser(Integer id, String newPassword) throws UserIdNotValidException {
        User user = getUserById(id);
        user.setPasswordHash(BCrypt.hashpw(
                newPassword,
                BCrypt.gensalt()
        ));
        user.setFirstTimeLogin(false);
        return userRepository.save(user);
    }
}
