package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.dtos.ProjectActiveBugsStatistic;
import com.adam.kiss.jbugger.dtos.ProjectUserStatistics;
import com.adam.kiss.jbugger.dtos.ViewMinMaxProjectBugs;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.StatisticOutputDataWithColor;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ProjectService projectService;
    private final UserService userService;
    private final BugService bugService;

    public List<StatisticOutputDataWithColor> getAssociatedNumberOfBugsForLabels(Project project) {
        return project.getLabelsOfProject().stream()
                .filter(label -> label.getBugsWithThisLabel().size() != 0)
                .map(label -> new StatisticOutputDataWithColor(
                        label.getLabelName(),
                        label.getBugsWithThisLabel().size(),
                        label.getBackgroundColor()))
                .collect(Collectors.toList());
    }

    public ProjectUserStatistics getProjectUserStatistics(Integer projectId) throws ProjectNotFoundException {
        Project project = projectService.getProjectById(projectId);

        ProjectUserStatistics projectUserStatistics = new ProjectUserStatistics();

        project.getUsersOfProject().forEach(
                user -> {
                    List<Bug> bugsClosedByUser = user.getBugsAssignedToTheUser()
                            .stream()
                            .filter(bug -> bug.getStatus().getStatusName().equalsIgnoreCase("CLOSED"))
                            .collect(Collectors.toList());
                    projectUserStatistics.getSolvedMostBugs().add(
                            new ProjectUserStatistics.UserWithNumberDto(
                                    user.getUsername(),
                                    user.getName(),
                                    bugsClosedByUser.size()
                            )
                    );
                    projectUserStatistics.getSolvedLeastBugs().add(
                            new ProjectUserStatistics.UserWithNumberDto(
                                    user.getUsername(),
                                    user.getName(),
                                    bugsClosedByUser.size()
                            )
                    );
                    projectUserStatistics.getCreatedMostBugs().add(
                            new ProjectUserStatistics.UserWithNumberDto(
                                    user.getUsername(),
                                    user.getName(),
                                    user.getBugsCreatedByTheUser().size()
                            )
                    );
                }
        );

        Comparator<ProjectUserStatistics.UserWithNumberDto> userWithNumberComparator =
                (u1, u2) -> u2.getNumber() - u1.getNumber();

        projectUserStatistics.setCreatedMostBugs(
                projectUserStatistics.getCreatedMostBugs()
                        .stream()
                        .sorted(userWithNumberComparator)
                        .collect(Collectors.toList())
        );
        projectUserStatistics.setSolvedLeastBugs(
                projectUserStatistics.getSolvedLeastBugs()
                        .stream()
                        .sorted(userWithNumberComparator)
                        .collect(Collectors.toList())
        );
        projectUserStatistics.setSolvedMostBugs(
                projectUserStatistics.getSolvedMostBugs()
                        .stream()
                        .sorted(userWithNumberComparator)
                        .collect(Collectors.toList())
        );
        return projectUserStatistics;
    }

    public List<ProjectActiveBugsStatistic> getProjectActiveBugsStatistics(Integer projectId, LocalDate startPeriod, LocalDate endPeriod)
            throws ProjectNotFoundException {
        Project project = projectService.getProjectById(projectId);

        List<Bug> bugsOfProject = project.getBugs();

        List<ProjectActiveBugsStatistic> projectActiveBugsStatistics = new ArrayList<>();

        LocalDate currentDateTime = LocalDate.from(startPeriod);

        while (currentDateTime.compareTo(endPeriod) <= 0) {
            int bugsActiveThatTime = 0;

            for (Bug bug :
                    bugsOfProject) {
                if (LocalDate.from(bug.getCreatedTime()).compareTo((currentDateTime)) <= 0
                        &&
                        (bug.getCloseTime() == null || LocalDate.from(bug.getCloseTime()).compareTo(currentDateTime) > 0)) {
                    bugsActiveThatTime++;
                }
            }
            projectActiveBugsStatistics.add(new ProjectActiveBugsStatistic(
                    LocalDate.from(currentDateTime),
                    bugsActiveThatTime)
            );
            currentDateTime = currentDateTime.plusDays(1);
        }

        return projectActiveBugsStatistics;
    }

    public ViewMinMaxProjectBugs getMinMaxProjectBugs() {
        List<Project> projects = projectService.getAllProjects();

        List<Project> projectsOrderedByBugs = projects
                .stream()
                .sorted(Comparator.comparingInt(p -> p.getBugs().size()))
                .collect(Collectors.toList());
        Project minProject = projectsOrderedByBugs.get(0);
        Project maxProject = projectsOrderedByBugs.get(projectsOrderedByBugs.size() - 1);

        ViewMinMaxProjectBugs viewMinMaxProjectBugs = new ViewMinMaxProjectBugs();

        if(minProject != null) {
            viewMinMaxProjectBugs.setMinProjectName(minProject.getName());
            viewMinMaxProjectBugs.setMinProjectBugsNr(minProject.getBugs().size());
            viewMinMaxProjectBugs.setMaxProjectNr(maxProject.getBugs().size());
            viewMinMaxProjectBugs.setMaxProjectName(maxProject.getName());
        }
        return viewMinMaxProjectBugs;
    }

}
