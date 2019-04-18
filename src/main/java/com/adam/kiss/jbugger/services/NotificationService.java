package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Notification;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.enums.NotificationType;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public void sendNotificationUserWasMentioned(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.USER_MENTIONED,
                "You were mentioned in a comment on a bug.",
                bug
        );
        notificationRepository.save(newNotification);
        userService.addNotificationToUser(mentionedUser, newNotification);
    }

    public void sendNotificationUsersBugWasUpdated(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.BUG_UPDATED,
                "A bug you created was updated.",
                bug
        );
        Notification newNotificationForSubscribers = new Notification(
                NotificationType.BUG_UPDATED,
                "A bug you subscribed to has been updated.",
                bug
        );
        notificationRepository.save(newNotification);
        notificationRepository.save(newNotificationForSubscribers);
        userService.addNotificationToUser(mentionedUser, newNotification);
        userService.addNotificationToUsers(bug.getUsersInterestedInChanges(), newNotificationForSubscribers);
    }

    public void sendNotificationUsersBugStatusWasUpdated(User mentionedUser, Bug bug) {
        Notification newNotificationForAuthor = new Notification(
                NotificationType.BUG_UPDATED,
                "A bug you created has new status.",
                bug
        );
        Notification newNotificationForSubscribers = new Notification(
                NotificationType.BUG_UPDATED,
                "A bug you subscribed to has a new status.",
                bug
        );
        notificationRepository.save(newNotificationForAuthor);
        notificationRepository.save(newNotificationForSubscribers);
        userService.addNotificationToUser(mentionedUser, newNotificationForAuthor);
        userService.addNotificationToUsers(bug.getUsersInterestedInChanges(), newNotificationForSubscribers);
    }

    public void sendNotificationUsersBugWasClosed(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.BUG_CLOSED,
                "A bug you created has been closed.",
                bug
        );
        Notification newNotificationForSubscribers = new Notification(
                NotificationType.BUG_CLOSED,
                "A bug you subscribed to has been closed.",
                bug
        );
        notificationRepository.save(newNotification);
        notificationRepository.save(newNotificationForSubscribers);
        userService.addNotificationToUser(mentionedUser, newNotification);
        userService.addNotificationToUsers(bug.getUsersInterestedInChanges(), newNotification);
        userService.addNotificationToUsers(bug.getUsersInterestedInChanges(), newNotificationForSubscribers);
    }

    public List<Notification> getNotificationsWhichWereNotSeen(User user) {
        return getAllNotificationsOfUser(user)
                .stream()
                .filter(notification -> !notification.getIsSeen())
                .collect(Collectors.toList());
    }

    public void userSawAllNotifications(User user) {
        getNotificationsWhichWereNotSeen(user)
                .forEach(notification -> {
                    notification.setIsSeen(true);
                    notificationRepository.save(notification);
                });
    }

    public Integer getNumberOfNotificationsOfUser(User user) {
        return getNotificationsWhichWereNotSeen(
                user
        ).size();
    }

    public List<Notification> getAllNotificationsOfUser(User user) {
        return notificationRepository.findAllByOrderByCreatedDesc()
                .stream()
                .filter(notification -> notification.getUserList().contains(user))
                .collect(Collectors.toList());
    }
}
