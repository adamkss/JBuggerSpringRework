package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Attachment;
import com.adam.kiss.jbugger.entities.Bug;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.repositories.AttachmentRepository;
import com.adam.kiss.jbugger.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> getAllStatuses(){
        return statusRepository.findAll();
    }

    public Status createStatus(String statusName){
        return statusRepository.save(new Status(statusName));
    }

    public Status getStatusByStatusName(String statusName) throws StatusNotFoundException {
        return statusRepository.findByStatusName(statusName).orElseThrow(StatusNotFoundException::new);
    }
}
