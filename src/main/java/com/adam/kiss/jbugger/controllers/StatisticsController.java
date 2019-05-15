package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ProjectActiveBugsStatistic;
import com.adam.kiss.jbugger.dtos.ProjectUserStatistics;
import com.adam.kiss.jbugger.dtos.ViewMinMaxProjectBugs;
import com.adam.kiss.jbugger.entities.StatisticOutputDataWithColor;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import com.adam.kiss.jbugger.services.ProjectService;
import com.adam.kiss.jbugger.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import utils.FormatHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/statistics")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final ProjectService projectService;

    @GetMapping("/byLabels/{projectId}")
    public List<StatisticOutputDataWithColor> getLabelsStatistics(@PathVariable Integer projectId)
            throws ProjectNotFoundException {
        return statisticsService.getAssociatedNumberOfBugsForLabels(projectService.getProjectById(projectId));
    }

    @GetMapping("/byUsers/{projectId}")
    public ProjectUserStatistics getProjectUserStatistics(@PathVariable Integer projectId) throws ProjectNotFoundException {
        return statisticsService.getProjectUserStatistics(projectId);
    }

    @GetMapping("/activeBugs/{projectId}/{startDate}/{endDate}")
    public List<ProjectActiveBugsStatistic> getProjectActiveBugsStatistics(@PathVariable Integer projectId,
                                                                           @PathVariable String startDate,
                                                                           @PathVariable String endDate)
            throws ProjectNotFoundException {
        return statisticsService.getProjectActiveBugsStatistics(
                projectId,
                LocalDate.parse(startDate, FormatHelper.LOCAL_DATE_FORMATTER),
                LocalDate.parse(endDate, FormatHelper.LOCAL_DATE_FORMATTER)
        );
    }

    @GetMapping("/minMaxBugs")
    public ViewMinMaxProjectBugs getMinMaxStatistics() {
        return statisticsService.getMinMaxProjectBugs();
    }
}
