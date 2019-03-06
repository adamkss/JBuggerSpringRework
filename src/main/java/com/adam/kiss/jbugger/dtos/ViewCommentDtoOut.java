package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewCommentDtoOut {

    private int id;
    private String comment;
    private ViewUserShortDtoOut author;
    private String createdDateTime;

    public static ViewCommentDtoOut mapCommentToViewCommentOutDto(Comment comment) {
        return ViewCommentDtoOut.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .author(ViewUserShortDtoOut.mapToDto(comment.getAuthor()))
                .createdDateTime(comment.getCreatedTime().toString())
                .build();
    }
}
