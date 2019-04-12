package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.services.BugService;
import lombok.Builder;
import lombok.Data;
import utils.FormatHelper;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ClosedStatisticsAboutBugDtoOut {

    private int bugId;
    private String bugTitle;
    private String severity;

    private String createdDateTime;
    private String closedDateTime;

    private int closedByUserId;
    private String closedByUserUsername;
    private String closedByUserName;

    private String durationDays;
    private String durationHours;
    private String durationMinutes;

    private List<ViewLabelDtoOut> labels;

    public static ClosedStatisticsAboutBugDtoOut mapToDto(BugService.ClosedBugInfo closedBugInfo) {
        return ClosedStatisticsAboutBugDtoOut.builder()
                .bugId(closedBugInfo.getBug().getId())
                .bugTitle(closedBugInfo.getBug().getTitle())
                .createdDateTime(FormatHelper.formatLocalDateTime(closedBugInfo.getCreatedTime()))
                .closedDateTime(FormatHelper.formatLocalDateTime(closedBugInfo.getClosedTime()))
                .closedByUserId(closedBugInfo.getClosedByUser().getId())
                .closedByUserUsername(closedBugInfo.getClosedByUser().getUsername())
                .closedByUserName(closedBugInfo.getClosedByUser().getName())
                .durationDays(String.valueOf(closedBugInfo.getDuration().toDays()))
                .durationHours(String.valueOf(closedBugInfo.getDuration().toHours()))
                .durationMinutes(String.valueOf(closedBugInfo.getDuration().toMinutes()))
                .labels(ViewLabelDtoOut.mapLabelListToDtoList(closedBugInfo.getBug().getLabels()))
                .severity(closedBugInfo.getBug().getSeverity().name())
                .build();
    }

    public static List<ClosedStatisticsAboutBugDtoOut> mapToDtoList(List<BugService.ClosedBugInfo> bugList) {
        return bugList.stream().map(ClosedStatisticsAboutBugDtoOut::mapToDto).collect(Collectors.toList());
    }
}
