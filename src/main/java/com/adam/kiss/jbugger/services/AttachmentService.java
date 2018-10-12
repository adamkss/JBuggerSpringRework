package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.repositories.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public Attachment createAttachment(Attachment attachment){
        return attachmentRepository.save(attachment);
    }

    public Optional<Attachment>  getAttachmentById(Integer id){
        return attachmentRepository.findById(id);
    }

    public void associateBugToAttachment(Bug bug, Attachment attachment){
        attachment.setBug(bug);
        attachmentRepository.save(attachment);
    }
}
