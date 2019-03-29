package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewAttachmentInfoDtoOut;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.exceptions.AttachmentNotFoundException;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.UserIdNotValidException;
import com.adam.kiss.jbugger.security.UserPrincipal;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.BugService;
import com.adam.kiss.jbugger.services.ChangeInBugService;
import com.adam.kiss.jbugger.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.omg.CORBA.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;

@RequestMapping("/attachments")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final BugService bugService;
    private final ChangeInBugService changeInBugService;
    private final UserService userService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<Integer> createAttachment(@RequestParam("fileUpload") MultipartFile multipartFile) {
        Attachment attachment = new Attachment();
        attachment.setContent(multipartFile.getBytes());
        attachment.setName(multipartFile.getOriginalFilename());
        attachmentService.createAttachment(attachment);
        return ResponseEntity.created(
                new URI("/attachment/" + URLEncoder.encode(String.valueOf(attachment.getId()), "UTF-8"))
        ).body(attachment.getId());
    }

    @GetMapping("/attachment/{id}")
    public Attachment getAttachmentById(@PathVariable Integer id) throws AttachmentNotFoundException {
        return attachmentService.getAttachmentById(id);
    }

    @GetMapping("attachment/{id}/blob")
    public byte[] getAttachmentBlobById(@PathVariable Integer id) throws AttachmentNotFoundException {
        return attachmentService.getAttachmentById(id).getContent();
    }

    @DeleteMapping("/attachment/{id}")
    public void deleteAttachment(@PathVariable Integer id,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws AttachmentNotFoundException, UserIdNotValidException {
        String attachmentName = attachmentService.getAttachmentById(id).getName();
        Bug associatedBug = attachmentService.getAttachmentById(id).getBug();

        attachmentService.deleteAttachmentById(id);

        changeInBugService.createChangeInBug(
                "Attachment \"" + attachmentName + "\" was deleted.",
                associatedBug,
                userService.getUserById(userPrincipal.getId())
        );
    }

    @PostMapping("/attachment/upload/{bugId}")
    public ViewAttachmentInfoDtoOut uploadAttachmentToBug(
            @PathVariable("bugId") Integer bugId,
            @RequestParam("file") MultipartFile multipartFile,
            @AuthenticationPrincipal UserPrincipal userPrincipal)
            throws BugNotFoundException, IOException, UserIdNotValidException {

        Attachment newAttachment = new Attachment(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        newAttachment = attachmentService.createAttachment(newAttachment);

        Bug associatedBug = bugService.getBugById(bugId);
        attachmentService.associateBugToAttachment(
                associatedBug,
                newAttachment
        );

        changeInBugService.createChangeInBug(
                "Attachment with name \""
                        + newAttachment.getName() +
                        "\" was added by \""
                        + userPrincipal.getUsername()
                        + "\".",
                associatedBug,
                userService.getUserById(userPrincipal.getId())
        );

        return ViewAttachmentInfoDtoOut.mapAttachmentToViewAttachmentInfoDtoOut(newAttachment);
    }

}
