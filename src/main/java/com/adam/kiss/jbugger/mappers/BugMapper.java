package com.adam.kiss.jbugger.mappers;

import com.adam.kiss.jbugger.dtos.CreateBugDtoIn;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.enums.Status;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.BugService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BugMapper {
    private final AttachmentService attachmentService;
    private final UserService userService;

    public Bug mapCreateBugDtoInToBug(CreateBugDtoIn createBugDtoIn) {
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
        bug.setStatus(createBugDtoIn.getStatus());

        return bug;
    }
}
