package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.dtos.CreateBugDtoIn;
import com.adam.kiss.jbugger.dtos.ViewBugOutDto;
import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.BugService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

@RequestMapping("/attachments")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

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
}
