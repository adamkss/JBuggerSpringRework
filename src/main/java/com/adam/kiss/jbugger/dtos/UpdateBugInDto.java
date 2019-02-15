package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.enums.Severity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBugInDto {
    private int id;

    private String title;

    private String description;

    private String revision;

    private String fixedInRevision;

    private Severity severity;

    private String status;

    private LocalDate targetDate;

    private String assignedToUsername;

    private List<ViewAttachmentInfoDtoOut> attachmentsInfo;
}
