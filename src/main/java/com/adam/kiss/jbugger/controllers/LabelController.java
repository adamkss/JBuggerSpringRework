package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewLabelDtoOut;
import com.adam.kiss.jbugger.dtos.ViewUserDtoOut;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.projections.UserWithNameAndUsernameProjection;
import com.adam.kiss.jbugger.services.LabelService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/labels")
@CrossOrigin
@RequiredArgsConstructor
public class LabelController {
    private final LabelService labelService;

    @GetMapping
    public List<ViewLabelDtoOut> getAllLabels(){
        return labelService.getAllLabels()
                .stream()
                .map(ViewLabelDtoOut::mapLabelToDto)
                .collect(Collectors.toList());
    }
}
