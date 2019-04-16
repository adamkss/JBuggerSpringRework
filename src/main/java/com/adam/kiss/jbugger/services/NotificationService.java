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

    public void sendNotificationUserWasMentioned(User mentionedUser, Bug bug) {
        Notification newNotification = new Notification(
                NotificationType.USER_MENTIONED,
                "You were mentioned in a comment on a bug.",
                bug
        );
        notificationRepository.save(newNotification);
        mentionedUser.getNotifications().add(newNotification);
    }
}
