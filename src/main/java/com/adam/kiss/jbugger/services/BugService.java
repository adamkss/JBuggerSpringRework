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

    public List<Bug> getAllBugs(){
        return bugRepository.findAll();
    }

    public Optional<Bug> getBugById(Integer id){
        return bugRepository.findById(id);
    }
}
