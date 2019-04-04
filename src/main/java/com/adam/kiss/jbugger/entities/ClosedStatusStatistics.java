package com.adam.kiss.jbugger.entities;


import com.adam.kiss.jbugger.dtos.ClosedStatisticsAboutBugDtoOut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClosedStatusStatistics {
    private List<ClosedStatisticsAboutBugDtoOut> bugStatistics;
    private long totalDays;
    private long totalHours;
    private long totalMinutes;

    private long averageMinutes;
    private long averageHours;
    private long averageDays;
}
