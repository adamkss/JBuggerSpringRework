package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.*;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Label;
import com.adam.kiss.jbugger.enums.PredefinedStatusNames;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.LabelNotFoundException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.mappers.BugMapper;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.BugService;
import com.adam.kiss.jbugger.services.LabelService;
import com.adam.kiss.jbugger.services.StatusService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/bugs")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class BugController {

    private final BugService bugService;
    private final BugMapper bugMapper;
    private final UserService userService;
    private final StatusService statusService;
    private final LabelService labelService;

    @GetMapping
    public List<ViewBugOutDto> getAllBugs(
            @RequestParam(
                    name = "filter",
                    defaultValue = "") String filter) {
        return ViewBugOutDto.mapToDtoList(bugService.getAllBugs(filter));
    }

    @GetMapping("/bug/{id}")
    public ViewBugOutDto getBugById(@PathVariable(name = "id") Integer id) throws BugNotFoundException {
        Bug bug = bugService.getBugById(id);
        return ViewBugOutDto.mapToDto(bug);
    }

    @PostMapping
    @SneakyThrows
    public ResponseEntity<ViewBugOutDto> createBug(@RequestBody CreateBugDtoIn createBugDtoIn,
                                                   @AuthenticationPrincipal UserPrincipal userPrincipal) {
        //TODO: Temporary, just for testing UI. No security yet.
        createBugDtoIn.setCreatedByUsername(userPrincipal.getUsername());

        Bug mappedBug = bugMapper.mapCreateBugDtoInToBug(createBugDtoIn);
        Bug savedBug = bugService.createBug(mappedBug);
        ViewBugOutDto viewBugOutDto = ViewBugOutDto.mapToDto(savedBug);

        return ResponseEntity.created(
                new URI("/bug/" + URLEncoder.encode(String.valueOf(savedBug.getId()), "UTF-8"))
        ).body(viewBugOutDto);
    }

    @GetMapping("/bugStatuses")
    public ResponseEntity<List<ViewStatusDtoOut>> getAllBugStatuses() {
        List<ViewStatusDtoOut> statusDtoOuts = statusService.getAllStatuses()
                .stream()
                .map(ViewStatusDtoOut::mapStatusToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(statusDtoOuts);
    }

    @PutMapping("/bug/{bugId}/status")
    public void updateBugStatus(@PathVariable(name = "bugId") Integer bugId,
                                @RequestBody UpdateBugStatusDTOIn updateBugStatusDTOIn) throws BugNotFoundException,
            StatusNotFoundException {
        bugService.updateBugStatus(bugId, statusService.getStatusByStatusName(updateBugStatusDTOIn.getNewStatus()));
    }

    @PutMapping("/bug/{bugId}")
    public ViewBugOutDto updateBug(@PathVariable(name = "bugId") Integer bugId,
                                   @RequestBody UpdateBugInDto updateBugInDto) throws BugNotFoundException {
        return ViewBugOutDto.mapToDto(
                bugService.updateBug(bugMapper.mapUpdateBugInDtoToBug(bugId, updateBugInDto))
        );
    }

    @PutMapping("/bug/{bugId}/labels")
    public ViewBugOutDto updateBugLabels(@PathVariable(name = "bugId") Integer bugId,
                                         @RequestBody UpdateBugLabelsInDto updateBugLabelsInDto) throws LabelNotFoundException, BugNotFoundException {
        List<Label> newLabels = new ArrayList<>();
        for (String labelName :
                updateBugLabelsInDto.getLabelsName()) {
            newLabels.add(labelService.getLabelByName(labelName));
        }

        return ViewBugOutDto.mapToDto(
                bugService.updateBugLabels(bugId, newLabels)
        );
    }
}
