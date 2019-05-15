package com.adam.kiss.jbugger.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewMinMaxProjectBugs {
    private String minProjectName;
    private Integer minProjectBugsNr;

    private String maxProjectName;
    private Integer maxProjectNr;
}
