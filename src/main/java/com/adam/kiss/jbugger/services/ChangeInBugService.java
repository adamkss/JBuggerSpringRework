package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.ChangeInBug;
import com.adam.kiss.jbugger.entities.User;
import com.adam.kiss.jbugger.repositories.ChangeInBugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChangeInBugService {
    private final ChangeInBugRepository changeInBugRepository;

    public ChangeInBug createChangeInBug(String changeText, Bug bug, User changeAuthor) {
        return changeInBugRepository.save(
                new ChangeInBug(changeText, bug, changeAuthor)
        );
    }

    public ChangeInBug createChangeInBug(String changeText, Bug bug, User changeAuthor, String fieldChanged,
                                         String oldValue, String newValue) {
        return changeInBugRepository.save(
                new ChangeInBug(changeText, bug, changeAuthor, fieldChanged, oldValue, newValue)
        );
    }
}
