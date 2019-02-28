package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    public Status createStatus(String statusName, String backgroundColor) {
        return statusRepository.save(new Status(statusName, backgroundColor));
    }

    public Status getStatusByStatusName(String statusName) throws StatusNotFoundException {
        return statusRepository.findByStatusName(statusName).orElseThrow(StatusNotFoundException::new);
    }

    public void deleteStatusWithBugs(String statusName) throws StatusNotFoundException {
        Status statusToDelete = getStatusByStatusName(statusName);
        statusRepository.delete(statusToDelete);
    }

    public void updateStatusName(String oldStatusName, String newStatusName) throws StatusNotFoundException {
        Status statusToUpdate = getStatusByStatusName(oldStatusName);
        statusToUpdate.setStatusName(newStatusName);
        statusRepository.save(statusToUpdate);
    }

    public void updateStatusColor(String statusName, String newStatusColor) throws StatusNotFoundException {
        Status statusToUpdate = getStatusByStatusName(statusName);
        statusToUpdate.setStatusColor(newStatusColor);
        statusRepository.save(statusToUpdate);
    }

}
