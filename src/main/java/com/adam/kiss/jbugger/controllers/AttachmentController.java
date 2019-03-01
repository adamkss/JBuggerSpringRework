package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.ViewAttachmentInfoDtoOut;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.exceptions.AttachmentNotFoundException;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.BugService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
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
    public Attachment getAttachmentById(@PathVariable Integer id) {
        return attachmentService.getAttachmentById(id).get();
    }

    @GetMapping("attachment/{id}/blob")
    public byte[] getAttachmentBlobById(@PathVariable Integer id) throws AttachmentNotFoundException {
        return attachmentService.getAttachmentById(id).orElseThrow(AttachmentNotFoundException::new).getContent();
    }

    @DeleteMapping("/attachment/{id}")
    public void deleteAttachment(@PathVariable Integer id) throws AttachmentNotFoundException {
        attachmentService.deleteAttachmentById(id);
    }

    @PostMapping("/attachment/upload/{bugId}")
    public ViewAttachmentInfoDtoOut uploadAttachmentToBug(@PathVariable("bugId") Integer bugId, @RequestParam("file") MultipartFile multipartFile) throws BugNotFoundException, IOException {
        Attachment newAttachment = new Attachment(multipartFile.getBytes(), multipartFile.getOriginalFilename());
        newAttachment = attachmentService.createAttachment(newAttachment);

        attachmentService.associateBugToAttachment(
                bugService.getBugById(bugId),
                newAttachment
        );

        return ViewAttachmentInfoDtoOut.mapAttachmentToViewAttachmentInfoDtoOut(newAttachment);
    }

}
