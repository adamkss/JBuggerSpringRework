package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.*;
import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.exceptions.*;
import com.adam.kiss.jbugger.mappers.BugMapper;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
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
            StatusNotFoundException, UserIdNotValidException, NothingChangedException {
        Bug affectedBug = bugService.getBugById(bugId);

        Status oldStatus = affectedBug.getStatus();
        Status newStatus = statusService.getStatusByStatusName(updateBugStatusDTOIn.getNewStatus());
        if (oldStatus.equals(newStatus)) {
            throw new NothingChangedException();
        }

        changeInBugService.createChangeInBug(
                "Status changed.",
                affectedBug,
                getUserByUserPrincipal(userPrincipal),
                "Status",
                oldStatus.getStatusName(),
                newStatus.getStatusName()
        );

        bugService.updateBugStatus(bugId, newStatus);
    }

    @PutMapping("/bug/{bugId}")
    public ViewBugOutDto updateBug(@PathVariable(name = "bugId") Integer bugId,
                                   @RequestBody UpdateBugInDto updateBugInDto,
                                   @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws BugNotFoundException, UserIdNotValidException {

        Bug bugToModify = bugService.getBugById(bugId);

        User author = this.getUserByUserPrincipal(userPrincipal);

        if (!bugToModify.getDescription().equals(updateBugInDto.getDescription())) {
            changeInBugService.createChangeInBug(
                    "Description changed.",
                    bugToModify,
                    author,
                    "Description",
                    bugToModify.getDescription(),
                    updateBugInDto.getDescription()
            );
        }

        if (!bugToModify.getTitle().equals(updateBugInDto.getTitle())) {
            changeInBugService.createChangeInBug(
                    "Title changed.",
                    bugToModify,
                    author,
                    "Title",
                    bugToModify.getTitle(),
                    updateBugInDto.getTitle()
            );
        }

        if (!bugToModify.getSeverity().equals(updateBugInDto.getSeverity())) {
            changeInBugService.createChangeInBug(
                    "Severity changed.",
                    bugToModify,
                    author,
                    "Severity",
                    bugToModify.getSeverity().toString(),
                    updateBugInDto.getSeverity().toString()
            );
        }

        if (!bugToModify.getRevision().equals(updateBugInDto.getRevision())) {
            changeInBugService.createChangeInBug(
                    "Revision changed.",
                    bugToModify,
                    author,
                    "Revision",
                    bugToModify.getRevision(),
                    updateBugInDto.getRevision()
            );
        }

        if (bugToModify.getTargetDate() == null && updateBugInDto.getTargetDate() != null) {
            changeInBugService.createChangeInBug(
                    "Target date changed.",
                    bugToModify,
                    author,
                    "Target date",
                    "Not set",
                    updateBugInDto.getTargetDate().toString()
            );
        }

        if (bugToModify.getTargetDate() != null
                && updateBugInDto.getTargetDate() != null) {
            if (!bugToModify.getTargetDate().equals(updateBugInDto.getTargetDate())) {
                changeInBugService.createChangeInBug(
                        "Target date changed.",
                        bugToModify,
                        author,
                        "Target date",
                        bugToModify.getTargetDate().toString(),
                        updateBugInDto.getTargetDate().toString()
                );
            }
        }

        if (bugToModify.getAssignedTo() == null && updateBugInDto.getAssignedToUsername() != null) {
            changeInBugService.createChangeInBug(
                    "User assigned to bug changed.",
                    bugToModify,
                    author,
                    "Assigned to",
                    "Not set",
                    updateBugInDto.getAssignedToUsername()
            );
        }

        if (bugToModify.getAssignedTo() != null && updateBugInDto.getAssignedToUsername() != null) {
            if (!bugToModify.getAssignedTo().getUsername().equals(updateBugInDto.getAssignedToUsername())) {
                changeInBugService.createChangeInBug(
                        "User assigned to bug changed.",
                        bugToModify,
                        author,
                        "Assigned to",
                        bugToModify.getAssignedTo().getUsername(),
                        updateBugInDto.getAssignedToUsername()
                );
            }
        }

        return ViewBugOutDto.mapToDto(
                bugService.updateBug(bugMapper.mapUpdateBugInDtoToBug(bugId, updateBugInDto))
        );
    }

    @PutMapping("/bug/{bugId}/labels")
    public ViewBugOutDto updateBugLabels(@PathVariable(name = "bugId") Integer bugId,
                                         @RequestBody UpdateBugLabelsInDto updateBugLabelsInDto)
            throws LabelNotFoundException, BugNotFoundException {
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
    public List<ViewChangeInBugDtoOut> getAllChangesOfABug(@PathVariable(name = "bugId") Integer bugId)
            throws BugNotFoundException {
        return bugService.getAllChangesOfABugById(bugId)
                .stream()
                .map(ViewChangeInBugDtoOut::mapChangeInBugToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/bug/{bugId}/assignToMyself")
    public void assignBugToUser(@PathVariable(name = "bugId") Integer bugId,
                                @AuthenticationPrincipal UserPrincipal currentUserPrincipal)
            throws BugNotFoundException, UserIdNotValidException {
        Bug bugToModify = bugService.getBugById(bugId);
        User userToAssign = getUserByUserPrincipal(currentUserPrincipal);

        bugService.assignBugToUser(bugToModify, userToAssign);
    }

    @DeleteMapping("bug/{bugId}")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public void deleteBug(@PathVariable(name = "bugId") Integer bugId) {
        bugService.deleteBug(bugId);
    }

    @PutMapping("bug/{bugId}/close")
    @Secured({"ROLE_ADM", "ROLE_PM"})
    public void closeBug(@PathVariable(name = "bugId") Integer bugId,
                         @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws NoClosedStatusException, BugNotFoundException, UserIdNotValidException {
        Bug affectedBug = bugService.getBugById(bugId);

        bugService.closeBug(bugId);
        changeInBugService.createChangeInBug(
                "Status changed.",
                affectedBug,
                getUserByUserPrincipal(userPrincipal),
                "Status",
                affectedBug.getStatus().getStatusName(),
                "CLOSED"
        );
    }

    @GetMapping("/closedStatistics")
    public ClosedStatusStatistics getClosedStatistics() {
        return bugService.calculateAverageCloseTimes();
    }
}
