package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.enums.Severity;
import com.adam.kiss.jbugger.enums.Status;
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

    private Status status;

    private LocalDate targetDate;

    private Integer assignedToUserId;

    private String assignedToName;

    private String assignedToUsername;

    private List<Integer> attachmentsIds;

    public static ViewBugOutDto mapToDto(Bug bug) {
        return ViewBugOutDto.builder()
                .id(bug.getId())
                .title(bug.getTitle())
                .description(bug.getDescription())
                .revision(bug.getRevision())
                .fixedInRevision(bug.getFixedInRevision())
                .severity(bug.getSeverity())
                .createdByUserId(bug.getCreatedBy().getId())
                .status(bug.getStatus())
                .assignedToUserId(bug.getAssignedTo() != null ? bug.getAssignedTo().getId() : null)
                .attachmentsIds(
                        bug.getAttachments().stream().map(Attachment::getId).collect(Collectors.toList())
                )
                .targetDate(bug.getTargetDate())
                .assignedToUserId(bug.getAssignedTo() != null ? bug.getAssignedTo().getId() : null)
                .assignedToName(bug.getAssignedTo() != null ? bug.getAssignedTo().getName() : null)
                .assignedToUsername(bug.getAssignedTo() != null ? bug.getAssignedTo().getUsername() : null)
                .build();
    }

    public static List<ViewBugOutDto> mapToDtoList(List<Bug> bugList) {
        return bugList.stream().map(ViewBugOutDto::mapToDto).collect(Collectors.toList());
    }
}
