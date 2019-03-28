package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.enums.Severity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewBugOutDto {

    private int id;

    private String title;

    private String description;

    private String revision;

    private String fixedInRevision;

    private Severity severity;

    private int createdByUserId;

    private String createdByUsername;

    private String status;

    private LocalDate targetDate;

    private Integer assignedToUserId;

    private String assignedToName;

    private String assignedToUsername;

    private List<ViewAttachmentInfoDtoOut> attachmentsInfo;

    private List<ViewLabelDtoOut> labels;

    public static ViewBugOutDto mapToDto(Bug bug) {
        return ViewBugOutDto.builder()
                .id(bug.getId())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .revision(bug.getRevision())
                .fixedInRevision(bug.getFixedInRevision())
                .severity(bug.getSeverity())
                .createdByUserId(bug.getCreatedBy().getId())
                .status(bug.getStatus().getStatusName())
                .assignedToUserId(bug.getAssignedTo() != null ? bug.getAssignedTo().getId() : null)
                .attachmentsInfo(
                        bug.getAttachments().stream().map(attachment ->
                                new ViewAttachmentInfoDtoOut(attachment.getId(), attachment.getName())
                        ).collect(Collectors.toList())
                )
                .targetDate(bug.getTargetDate())
                .assignedToUserId(bug.getAssignedTo() != null ? bug.getAssignedTo().getId() : null)
                .assignedToName(bug.getAssignedTo() != null ? bug.getAssignedTo().getName() : null)
                .assignedToUsername(bug.getAssignedTo() != null ? bug.getAssignedTo().getUsername() : null)
                .labels(bug.getLabels().stream().map(ViewLabelDtoOut::mapLabelToDto).collect(Collectors.toList()))
                .createdByUsername(bug.getCreatedBy().getUsername())
                .build();
    }

    public static List<ViewBugOutDto> mapToDtoList(List<Bug> bugList) {
        return bugList.stream().map(ViewBugOutDto::mapToDto).collect(Collectors.toList());
    }
}
