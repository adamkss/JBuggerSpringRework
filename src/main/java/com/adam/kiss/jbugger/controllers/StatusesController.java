package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateStatusDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusColorDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusNameDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateStatusOrderDtoIn;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.services.StatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/statuses")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class StatusesController {

    private final StatusService statusService;

    @GetMapping
    public List<Status> getStatuses() {
        return statusService.getAllStatuses();
    }

    @PostMapping
    public Status createStatus(@RequestBody CreateStatusDtoIn createStatusDtoIn) {
        return statusService.createStatus(createStatusDtoIn.getStatusName(), createStatusDtoIn.getStatusColor());
    }

    @DeleteMapping("/{statusName}")
    public void deleteStatus(@PathVariable(name = "statusName") String statusName) throws StatusNotFoundException {
        statusService.deleteStatusWithBugs(statusName);
    }

    @PutMapping("/{statusName}/name")
    public void updateStatusName(@PathVariable(name = "statusName") String statusNameOld,
                                 @RequestBody UpdateStatusNameDtoIn updateStatusNameDtoIn) throws StatusNotFoundException {
        statusService.updateStatusName(statusNameOld, updateStatusNameDtoIn.getStatusName());
    }

    @PutMapping("/{statusName}/color")
    public void updateStatusName(@PathVariable(name = "statusName") String statusName,
                                 @RequestBody UpdateStatusColorDtoIn updateStatusColorDtoIn) throws StatusNotFoundException {
        statusService.updateStatusColor(statusName, updateStatusColorDtoIn.getStatusColor());
    }

    @PutMapping("/order")
    public void updateStatusOrder(@RequestBody UpdateStatusOrderDtoIn updateStatusOrderDtoIn) throws StatusNotFoundException {
        statusService.reorderStatusesByChangedStatusOrder(
                updateStatusOrderDtoIn.getOldOrder(),
                updateStatusOrderDtoIn.getNewOrder()
        );
    }
}
