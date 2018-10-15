package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.repositories.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BugService {
    private final BugRepository bugRepository;
    private final AttachmentService attachmentService;

    public List<Bug> getAllBugs(String filter){
        return bugRepository.findAllFiltered(filter);
    }

    public Optional<Bug> getBugById(Integer id){
        return bugRepository.findById(id);
    }

    public Bug createBug(Bug bug){
        bugRepository.save(bug);
        attachmentService.associateBugToAttachments(bug, bug.getAttachments());
        return bug;
    }
}
