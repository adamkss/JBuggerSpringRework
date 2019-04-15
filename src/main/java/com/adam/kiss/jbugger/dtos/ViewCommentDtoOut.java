package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Comment;
import com.adam.kiss.jbugger.entities.PartialCommentUserMention;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.FormatHelper;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCommentDtoOut {

    private int id;
    private String comment;
    private ViewUserShortDtoOut author;
    private String createdDateTime;
    private List<ViewPartialCommentUserMentionDtoOut> partialCommentUserMentions;

    public static ViewCommentDtoOut mapCommentToViewCommentOutDto(Comment comment) {
        return ViewCommentDtoOut.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .author(ViewUserShortDtoOut.mapToDto(comment.getAuthor()))
                .createdDateTime(
                        FormatHelper.formatLocalDateTime(
                                comment.getCreatedTime()
                        )
                )
                .partialCommentUserMentions(
                        comment.getPartialCommentUserMentions()
                        .stream()
                        .map(ViewPartialCommentUserMentionDtoOut::mapToDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
