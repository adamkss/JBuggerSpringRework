package com.adam.kiss.jbugger.mappers;

import com.adam.kiss.jbugger.dtos.CreateBugDtoIn;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.StatusService;
import com.adam.kiss.jbugger.services.UserService;
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

    public Bug mapCreateBugDtoInToBug(CreateBugDtoIn createBugDtoIn) throws StatusNotFoundException {
        List<Attachment> relatedAttachments = attachmentService.getAttachmentsByIds(createBugDtoIn.getAttachmentIds())
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        Bug bug = new Bug();
        bug.setTitle(createBugDtoIn.getTitle());
        bug.setDescription(createBugDtoIn.getDescription());
        bug.setSeverity(createBugDtoIn.getSeverity());
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

        Status status = statusService.getStatusByStatusName(createBugDtoIn.getStatus());
        bug.setStatus(status);

        return bug;
    }
}
