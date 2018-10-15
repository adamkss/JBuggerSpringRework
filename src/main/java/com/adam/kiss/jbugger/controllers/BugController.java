package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateBugDtoIn;
import com.adam.kiss.jbugger.dtos.ViewBugOutDto;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.mappers.BugMapper;
import com.adam.kiss.jbugger.services.BugService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@RequestMapping("/bugs")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;
    private final BugMapper bugMapper;

    @GetMapping
    public List<ViewBugOutDto> getAllBugs(
            @RequestParam(
                    name = "filter",
                    defaultValue = "") String filter) {
        return ViewBugOutDto.mapToDtoList(bugService.getAllBugs(filter));
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

    @PostMapping
    @SneakyThrows
    public ResponseEntity<ViewBugOutDto> createBug(@RequestBody CreateBugDtoIn createBugDtoIn){
        Bug mappedBug = bugMapper.mapCreateBugDtoInToBug(createBugDtoIn);
        Bug savedBug = bugService.createBug(mappedBug);

        ViewBugOutDto viewBugOutDto = ViewBugOutDto.mapToDto(savedBug);

        return ResponseEntity.created(
                new URI("/bug/" + URLEncoder.encode(String.valueOf(savedBug.getId()), "UTF-8"))
        ).body(viewBugOutDto);
    }
}
