package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateStatusDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusColorDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusNameDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusOrderDtoIn;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.services.ProjectService;
import com.adam.kiss.jbugger.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/statuses")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class StatusesController {

    private final StatusService statusService;
    private final ProjectService projectService;

    @GetMapping("/{projectId}")
    public List<Status> getStatuses(@PathVariable Integer projectId) throws ProjectNotFoundException {
        projectService.getProjectById(projectId)
        ;
        return statusService.getAllStatusesOfProject(projectService.getProjectById(projectId));
    }

    @PostMapping("/{projectId}")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public Status createStatus(
            @PathVariable(name = "projectId")Integer projectId,
            @RequestBody CreateStatusDtoIn createStatusDtoIn) throws ProjectNotFoundException {
        return projectService.createStatusInProject(
                projectId,
                createStatusDtoIn.getStatusName(),
                createStatusDtoIn.getStatusColor()
        );
    }

    @DeleteMapping("/{statusId}")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public void deleteStatus(@PathVariable Integer statusId) throws StatusNotFoundException {
        statusService.deleteStatusWithBugs(statusId);
    }

    @PutMapping("/{statusId}/name")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public void updateStatusName(@PathVariable Integer statusId,
                                 @RequestBody UpdateStatusNameDtoIn updateStatusNameDtoIn) throws StatusNotFoundException {
        statusService.updateStatusName(statusId, updateStatusNameDtoIn.getStatusName());
    }

    @PutMapping("/{statusId}/color")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public void updateStatusName(@PathVariable Integer statusId,
                                 @RequestBody UpdateStatusColorDtoIn updateStatusColorDtoIn) throws StatusNotFoundException {
        statusService.updateStatusColor(statusId, updateStatusColorDtoIn.getStatusColor());
    }

    @PutMapping("/{projectId}/order")
    public void updateStatusOrder(
            @PathVariable Integer projectId,
            @RequestBody UpdateStatusOrderDtoIn updateStatusOrderDtoIn
    ) throws StatusNotFoundException, ProjectNotFoundException {
        statusService.reorderStatusesByChangedStatusOrder(
                projectService.getProjectById(projectId),
                updateStatusOrderDtoIn.getOldOrder(),
                updateStatusOrderDtoIn.getNewOrder()
        );
    }
}
