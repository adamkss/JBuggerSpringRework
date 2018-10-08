package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewBugOutDto;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.services.BugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/bug/{id}")
    public ResponseEntity<ViewBugOutDto> getBugById(@PathVariable(name ="id") Integer id){
        Optional<Bug> bug = bugService.getBugById(id);

        if(bug.isPresent()){
            return ResponseEntity.ok(
                    ViewBugOutDto.mapToDto(bug.get())
            );
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
