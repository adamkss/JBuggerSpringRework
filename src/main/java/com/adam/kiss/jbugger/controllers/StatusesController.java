package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateStatusDtoIn;
import com.adam.kiss.jbugger.entities.Status;
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
}
