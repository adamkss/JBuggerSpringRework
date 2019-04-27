package com.adam.kiss.jbugger.mappers;

import com.adam.kiss.jbugger.dtos.CreateBugDtoIn;
import com.adam.kiss.jbugger.dtos.UpdateBugInDto;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.enums.Severity;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.LabelNotFoundException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BugMapper {
    private final AttachmentService attachmentService;
    private final UserService userService;
    private final StatusService statusService;
    private final BugService bugService;
    private final LabelService labelService;

    public Bug mapCreateBugDtoInToBug(CreateBugDtoIn createBugDtoIn) throws StatusNotFoundException {
        List<Attachment> relatedAttachments = attachmentService.getAttachmentsByIds(createBugDtoIn.getAttachmentIds())
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        Bug bug = new Bug();
        bug.setTitle(createBugDtoIn.getTitle());
        bug.setDescription(createBugDtoIn.getDescription());
        bug.setSeverity(createBugDtoIn.getSeverity() == null ? Severity.LOW : createBugDtoIn.getSeverity());
        bug.setAssignedTo(
                userService.getUserByUsername(createBugDtoIn.getAssignedToUsername())
        );
        bug.setTargetDate(createBugDtoIn.getTargetDate());
        bug.setCreatedBy(
                userService.getUserByUsername(createBugDtoIn.getCreatedByUsername())
        );
        bug.setAttachments(relatedAttachments);

        //Fields not present in the DTO
        bug.setRevision("1.0");

        bug.setLabels(
                createBugDtoIn.getLabelsIds()
                .stream()
                .map(labelId -> {
                    try {
                        return labelService.getLabelById(labelId);
                    } catch (LabelNotFoundException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .collect(Collectors.toList())
        );
        return bug;
    }

    public Bug mapUpdateBugInDtoToBug(Integer bugId, UpdateBugInDto updateBugInDto) throws BugNotFoundException {
        //change only the differences
        Bug bug = bugService.getBugById(bugId);
        bug.setTitle(updateBugInDto.getTitle());
        bug.setAssignedTo(
                userService.getUserByUsername(updateBugInDto.getAssignedToUsername())
        );
        bug.setSeverity(updateBugInDto.getSeverity());
        bug.setRevision(updateBugInDto.getRevision());
        bug.setTargetDate(updateBugInDto.getTargetDate());
        bug.setDescription(updateBugInDto.getDescription());
        return bug;
    }
}
