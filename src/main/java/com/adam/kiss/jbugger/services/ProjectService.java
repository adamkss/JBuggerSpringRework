package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.LabelWithThisNameAlreadyExistsException;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import com.adam.kiss.jbugger.repositories.AttachmentRepository;
import com.adam.kiss.jbugger.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final BugService bugService;
    private final LabelService labelService;
    private final StatusService statusService;

    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Project getProjectById(Integer projectId) throws ProjectNotFoundException {
        return projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new);
    }

    public List<Bug> getAllBugsOfProject(Integer projectId) throws ProjectNotFoundException {
        return getProjectById(projectId).getBugs();
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public List<Label> getAllLabelsOfProject(Integer projectId) throws ProjectNotFoundException {
        return getProjectById(projectId).getLabelsOfProject();
    }

    public Bug createBugInProject(Integer projectId, Bug bug) throws ProjectNotFoundException {
        Project project = getProjectById(projectId);
        bug.setProject(project);
        Bug createdBug = bugService.createBug(bug);
        project.getBugs().add(bug);
        projectRepository.save(project);
        return createdBug;
    }

    public List<Status> getStatusesOfProject(Integer projectId) throws ProjectNotFoundException {
        return getProjectById(projectId).getProjectStatuses();
    }

    public Label createLabelInProject(Integer projectId, Label label)
            throws ProjectNotFoundException, LabelWithThisNameAlreadyExistsException {
        Project project = getProjectById(projectId);

        label.setProject(project);
        Label createdLabel = labelService.createLabel(label);

        project.getLabelsOfProject().add(createdLabel);
        projectRepository.save(project);
        return createdLabel;
    }

    public Status createStatusInProject(Integer projectId, String statusName, String statusColor) throws ProjectNotFoundException {
        Project project = getProjectById(projectId);

        Status createdStatus = statusService.createStatus(project, statusName, statusColor);
        project.getProjectStatuses().add(createdStatus);
        projectRepository.save(project);

        return createdStatus;
    }

}