package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Notification;
import com.adam.kiss.jbugger.entities.Role;
import com.adam.kiss.jbugger.entities.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewUserDtoOut {
    private int id;

    private String name;

    private String phoneNumber;

    private String email;

    private boolean userActivated;

    private List<Integer> notificationsIds = new ArrayList<>();

    private Integer rolesId;

    private String username;

    private List<Integer> bugsAssignedToTheUserIds = new ArrayList<>();

    public static ViewUserDtoOut mapToDto(User user){
        return ViewUserDtoOut.builder()
                .id(user.getId())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userActivated(user.isUserActivated())
                .notificationsIds(
                        user.getNotifications().stream().map(Notification::getId).collect(Collectors.toList())
                )
                .rolesId(user.getRole().getId())
                .username(user.getUsername())
                .bugsAssignedToTheUserIds(
                        user.getBugsAssignedToTheUser().stream().map(Bug::getId).collect(Collectors.toList())
                )
                .build();
    }

    public static List<ViewUserDtoOut> mapToDtoList(List<User> bugList) {
        return bugList.stream().map(ViewUserDtoOut::mapToDto).collect(Collectors.toList());
    }
}

