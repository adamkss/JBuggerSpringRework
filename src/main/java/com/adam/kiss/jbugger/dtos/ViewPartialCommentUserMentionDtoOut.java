package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.PartialCommentUserMention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewPartialCommentUserMentionDtoOut {

    private int id;

    private String content;
    private boolean isUserRelevant;
    private Integer userId;
    private String username;
    private String usersName;

    public static ViewPartialCommentUserMentionDtoOut mapToDto(PartialCommentUserMention partialCommentUserMention) {
        return ViewPartialCommentUserMentionDtoOut.builder()
                .id(partialCommentUserMention.getId())
                .content(partialCommentUserMention.getContent())
                .isUserRelevant(partialCommentUserMention.isUserRelevant())
                .userId(partialCommentUserMention.getUserId())
                .username(partialCommentUserMention.getUsername())
                .usersName(partialCommentUserMention.getUsersName())
                .build();
    }
}
