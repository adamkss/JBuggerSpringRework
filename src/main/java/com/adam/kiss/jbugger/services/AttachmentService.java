package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.exceptions.AttachmentNotFoundException;
import com.adam.kiss.jbugger.repositories.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;

    public Attachment createAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public Optional<Attachment> getAttachmentById(Integer id) {
        return attachmentRepository.findById(id);
    }

    public void associateBugToAttachment(Bug bug, Attachment attachment) {
        attachment.setBug(bug);
        attachmentRepository.save(attachment);
    }

    public void associateBugToAttachments(Bug bug, List<Attachment> attachments) {
        attachments.forEach(attachment -> {
            associateBugToAttachment(bug, attachment);
        });
    }

    public List<Optional<Attachment>> getAttachmentsByIds(List<Integer> ids) {
        return ids.stream()
                .map(id -> attachmentRepository.findById(id))
                .collect(Collectors.toList());
    }

    public void deleteAttachmentById(Integer id) throws AttachmentNotFoundException {
        attachmentRepository.delete(getAttachmentById(id).orElseThrow(AttachmentNotFoundException::new));
    }
}
