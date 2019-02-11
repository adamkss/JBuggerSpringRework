package com.adam.kiss.jbugger.controllers;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.services.AttachmentService;
import com.adam.kiss.jbugger.services.StatusService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;

@RequestMapping("/statuses")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class StatusesController {

    private final StatusService statusService;

    @GetMapping
    public List<Status> getStatuses(){
        return statusService.getAllStatuses();
    }
}
