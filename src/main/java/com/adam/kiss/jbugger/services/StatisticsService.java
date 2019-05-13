package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.dtos.ProjectUserStatistics;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.StatisticOutputDataWithColor;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
