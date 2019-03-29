package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.*;
import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.enums.PredefinedStatusNames;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.LabelNotFoundException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.mappers.BugMapper;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.*;
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
    private final ChangeInBugService changeInBugService;

    private User getUserByUserPrincipal(UserPrincipal userPrincipal) throws UserIdNotValidException {
        return userService.getUserById(userPrincipal.getId());
    }

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

        changeInBugService.createChangeInBug(
                "Bug created.",
                savedBug,
                getUserByUserPrincipal(userPrincipal)
        );

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
                                @RequestBody UpdateBugStatusDTOIn updateBugStatusDTOIn,
                                @AuthenticationPrincipal UserPrincipal userPrincipal) throws BugNotFoundException,
            StatusNotFoundException, UserIdNotValidException {
        Bug affectedBug = bugService.getBugById(bugId);

        Status oldStatus = affectedBug.getStatus();
        Status newStatus = statusService.getStatusByStatusName(updateBugStatusDTOIn.getNewStatus());

        changeInBugService.createChangeInBug(
                String.format("Changed status from %s to %s.", oldStatus.getStatusName(), newStatus.getStatusName()),
                affectedBug,
                getUserByUserPrincipal(userPrincipal)
        );

        bugService.updateBugStatus(bugId, newStatus);
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

    @GetMapping("/bug/{bugId}/history")
    public List<ViewChangeInBugDtoOut> getAllChangesOfABug(@PathVariable(name = "bugId") Integer bugId) throws BugNotFoundException {
        return bugService.getAllChangesOfABugById(bugId)
                .stream()
                .map(ViewChangeInBugDtoOut::mapChangeInBugToDto)
                .collect(Collectors.toList());
    }
}
