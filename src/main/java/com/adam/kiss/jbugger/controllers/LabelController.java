package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateLabelDtoIn;
import com.adam.kiss.jbugger.dtos.ViewLabelDtoOut;
import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.exceptions.LabelWithThisNameAlreadyExistsException;
import com.adam.kiss.jbugger.exceptions.ProjectNotFoundException;
import com.adam.kiss.jbugger.services.LabelService;
import com.adam.kiss.jbugger.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/labels")
@CrossOrigin
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;
    private final ProjectService projectService;

    @GetMapping
    public List<ViewLabelDtoOut> getAllLabels() {
        return labelService.getAllLabels()
                .stream()
                .map(ViewLabelDtoOut::mapLabelToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/project/{projectId}")
    public List<ViewLabelDtoOut> getAllLabelsOfProject() {
        return labelService.getAllLabels()
                .stream()
                .map(ViewLabelDtoOut::mapLabelToDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/{projectId}")
    public ViewLabelDtoOut createLabel(
            @PathVariable(name = "projectId") Integer projectId,
            @RequestBody CreateLabelDtoIn createLabelDtoIn)
            throws LabelWithThisNameAlreadyExistsException, ProjectNotFoundException {
        Label labelToCreate = new Label();
        labelToCreate.setBackgroundColor(createLabelDtoIn.getLabelColor());
        labelToCreate.setLabelName(createLabelDtoIn.getLabelName());

        return ViewLabelDtoOut.mapLabelToDto(
                projectService.createLabelInProject(projectId, labelToCreate)
        );
    }
}
