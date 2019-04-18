package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.enums.Severity;
import lombok.Builder;
import lombok.Data;
import utils.FormatHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewBugWithStarredStarsOutDto {

    private int id;

    private String title;

    private String description;

    private String revision;

    private String fixedInRevision;

    private Severity severity;

    private int createdByUserId;

    private String createdAtDateTime;

    private String createdByUsername;

    private String status;

    private LocalDate targetDate;

    private Integer assignedToUserId;

    private String assignedToName;

    private String assignedToUsername;

    private List<ViewAttachmentInfoDtoOut> attachmentsInfo;

    private List<ViewLabelDtoOut> labels;

    private List<ViewUserShortDtoOut> interestedUsers;

    private boolean isCurrentUserInterestedInMe;

    public static ViewBugWithStarredStarsOutDto mapToDto(Bug bug, User user) {
        return ViewBugWithStarredStarsOutDto.builder()
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
                .createdAtDateTime(FormatHelper.formatLocalDateTime(bug.getCreatedTime()))
                .isCurrentUserInterestedInMe(bug.getUsersInterestedInChanges().contains(user))
                .interestedUsers(
                        bug.getUsersInterestedInChanges()
                                .stream()
                                .map(ViewUserShortDtoOut::mapToDto)
                                .collect(Collectors.toList()
                                )
                )
                .build();
    }

    public static List<ViewBugWithStarredStarsOutDto> mapToDtoList(List<Bug> bugList, User user) {
        return bugList.stream().map(bug -> mapToDto(bug, user)).collect(Collectors.toList());
    }
}
