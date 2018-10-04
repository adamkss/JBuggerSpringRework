package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewBugOutDto;
import com.adam.kiss.jbugger.services.BugService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/bugs")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;

    @GetMapping
    public List<ViewBugOutDto> getAllBugs() {
        return ViewBugOutDto.mapToDtoList(bugService.getAllBugs());
    }
}
