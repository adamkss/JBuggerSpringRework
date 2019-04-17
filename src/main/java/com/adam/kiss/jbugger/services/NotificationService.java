package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Notification;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.enums.NotificationType;
import com.adam.kiss.jbugger.repositories.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        notificationRepository.save(newNotification);
        userService.addNotificationToUser(mentionedUser, newNotification);
    }

    public void sendNotificationUsersBugStatusWasUpdated(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.BUG_UPDATED,
                "A bug you created has new status.",
                bug
        );
        notificationRepository.save(newNotification);
        userService.addNotificationToUser(mentionedUser, newNotification);
    }

    public void sendNotificationUsersBugWasClosed(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.BUG_CLOSED,
                "A bug you created has been closed.",
                bug
        );
        notificationRepository.save(newNotification);
        userService.addNotificationToUser(mentionedUser, newNotification);
    }
}
