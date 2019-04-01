package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
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

    public Bug getBugById(Integer id) throws BugNotFoundException{
        return bugRepository.findById(id).orElseThrow(BugNotFoundException::new);
    }

    public Bug createBug(Bug bug){
        bugRepository.save(bug);
        attachmentService.associateBugToAttachments(bug, bug.getAttachments());
        return bug;
    }

    public void updateBugStatus(Integer bugId, Status status) throws BugNotFoundException {
        Optional<Bug> bugToUpdateStatus = bugRepository.findById(bugId);
        Bug bugToUpdate = bugToUpdateStatus.orElseThrow(BugNotFoundException::new);
        bugToUpdate.setStatus(status);
        bugRepository.save(bugToUpdate);
    }

    public Bug updateBug(Bug bugToUpdate){
        return bugRepository.save(bugToUpdate);
    }

    public Bug updateBugLabels(Integer bugId, List<Label> labels) throws BugNotFoundException {
        Bug bug = bugRepository.findById(bugId).orElseThrow(BugNotFoundException::new);
        bug.setLabels(labels);
        bugRepository.save(bug);
        return bug;
    }

    public List<ChangeInBug> getAllChangesOfABugById(Integer bugId) throws BugNotFoundException {
        return bugRepository
                .findById(bugId).orElseThrow(BugNotFoundException::new)
                .getChangesOfBug();
    }

    public void assignBugToUser(Bug bug, User user){
        bug.setAssignedTo(user);
        bugRepository.save(bug);

    }
}
