package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.User;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewUserForAdminDtoOut {
    private int id;

    private String name;

    private String phoneNumber;

    private String email;

    private boolean userActivated;

    private String role;

    private String username;

    private List<Integer> bugsAssignedToTheUserIds = new ArrayList<>();

    public static ViewUserForAdminDtoOut mapToDto(User user){
        return ViewUserForAdminDtoOut.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .userActivated(user.isUserActivated())
                .role(user.getRole().getRoleName())
                .username(user.getUsername())
                .bugsAssignedToTheUserIds(
                        user.getBugsAssignedToTheUser().stream().map(Bug::getId).collect(Collectors.toList())
                )
                .build();
    }

    public static List<ViewUserForAdminDtoOut> mapToDtoList(List<User> bugList) {
        return bugList.stream().map(ViewUserForAdminDtoOut::mapToDto).collect(Collectors.toList());
    }
}

