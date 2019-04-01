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
        return statusRepository.findAllByOrderByOrderNrAsc();
    }

    private void shiftStatuses() {
        List<Status> allStatuses = getAllStatuses();
        allStatuses.forEach(status -> status.setOrderNr(status.getOrderNr() + 1));

        statusRepository.saveAll(allStatuses);
    }

    public Status createStatus(String statusName, String backgroundColor) {
        this.shiftStatuses();
        return statusRepository.save(new Status(statusName, backgroundColor, 0));
    }

    public Status createStatusWithOrder(String statusName, String backgroundColor, int order){
        return statusRepository.save(new Status(statusName, backgroundColor, order));
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

    public void reorderStatusesByChangedStatusOrder(int oldOrder, int newOrder) throws StatusNotFoundException {
        Status statusOrderChanged = this.statusRepository.findByOrderNr(oldOrder);

        List<Status> allStatuses = getAllStatuses();

        if (newOrder < oldOrder) {
            for (int i = newOrder; i < oldOrder; i++) {
                Status currentStatus = statusRepository.findByOrderNr(i);
                currentStatus.incrementOrderNr();
            }
        }

        if (newOrder > oldOrder) {
            for (int i = oldOrder + 1; i <= newOrder; i++) {
                Status currentStatus = statusRepository.findByOrderNr(i);
                currentStatus.decrementOrderNr();
            }
        }

        statusRepository.saveAll(allStatuses);

        statusOrderChanged.setOrderNr(newOrder);
        statusRepository.save(statusOrderChanged);
    }
}
