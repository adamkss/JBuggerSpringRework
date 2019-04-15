package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Not;
import utils.FormatHelper;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewNotificationDtoOut {
    private int id;
    private String notificationType;
    private String text;
    private boolean isRelatedToBug;

    private int bugId;
    private String bugTitle;

    private String createdTime;

    public static ViewNotificationDtoOut mapNotificationToDTO(Notification notification) {
        ViewNotificationDtoOutBuilder builder = ViewNotificationDtoOut.builder()
                .id(notification.getId())
                .notificationType(notification.getNotificationType().toString())
                .text(notification.getText())
                .isRelatedToBug(notification.isRelatedToBug())
                .createdTime(FormatHelper.formatLocalDateTime(
                        notification.getCreated()
                ));
        if (notification.getBug() != null) {
            builder.bugId(notification.getBug().getId());
            builder.bugTitle(notification.getBug().getTitle());
        }
        return builder.build();
    }

    public static List<ViewNotificationDtoOut> mapNotificationsListToDTOList(List<Notification> notifications) {
        return notifications
                .stream()
                .map(ViewNotificationDtoOut::mapNotificationToDTO)
                .collect(Collectors.toList());
    }
}

