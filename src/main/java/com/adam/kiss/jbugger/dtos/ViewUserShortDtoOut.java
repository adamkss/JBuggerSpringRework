package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewUserShortDtoOut {
    private int id;

    private String name;

    private String username;

    public static ViewUserShortDtoOut mapToDto(User user) {
        return ViewUserShortDtoOut.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .build();
    }
}

