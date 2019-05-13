package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ProjectUserStatistics;
import com.adam.kiss.jbugger.entities.StatisticOutputDataWithColor;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import com.adam.kiss.jbugger.services.ProjectService;
import com.adam.kiss.jbugger.services.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
}
