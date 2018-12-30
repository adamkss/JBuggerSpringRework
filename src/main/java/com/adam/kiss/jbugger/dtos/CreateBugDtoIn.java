package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.enums.Severity;
import com.adam.kiss.jbugger.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateBugDtoIn {
    private String title;
    private String description;
    private Status status;
    private LocalDate targetDate;
    private Severity severity;
    private String createdByUsername;
    private String assignedToUsername;
    private List<Integer> attachmentIds;
}
