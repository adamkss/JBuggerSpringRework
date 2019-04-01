package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateLabelDtoIn;
import com.adam.kiss.jbugger.dtos.ViewLabelDtoOut;
import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.exceptions.LabelWithThisNameAlreadyExistsException;
import com.adam.kiss.jbugger.services.LabelService;
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

    @GetMapping
    public List<ViewLabelDtoOut> getAllLabels() {
        return labelService.getAllLabels()
                .stream()
                .map(ViewLabelDtoOut::mapLabelToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ViewLabelDtoOut createLabel(@RequestBody CreateLabelDtoIn createLabelDtoIn)
            throws LabelWithThisNameAlreadyExistsException {
        Label labelToCreate = new Label();
        labelToCreate.setBackgroundColor(createLabelDtoIn.getLabelColor());
        labelToCreate.setLabelName(createLabelDtoIn.getLabelName());

        return ViewLabelDtoOut.mapLabelToDto(
                labelService.createLabel(labelToCreate)
        );
    }
}
