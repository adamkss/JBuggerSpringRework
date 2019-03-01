package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewAttachmentInfoDtoOut {
    private Integer id;
    private String name;

    public static ViewAttachmentInfoDtoOut mapAttachmentToViewAttachmentInfoDtoOut(Attachment attachment) {
        return ViewAttachmentInfoDtoOut.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .build();
    }
}
