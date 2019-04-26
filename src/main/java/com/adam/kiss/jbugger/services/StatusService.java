package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.Project;
import com.adam.kiss.jbugger.entities.Status;
import com.adam.kiss.jbugger.exceptions.NoClosedStatusException;
import com.adam.kiss.jbugger.exceptions.StatusNotFoundException;
import com.adam.kiss.jbugger.repositories.StatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatusService {
    private final StatusRepository statusRepository;

    public List<Status> getAllStatusesOfProject(Project project) {
        return statusRepository.findAllByProjectOrderByOrderNrAsc(project);
    }

    public Status getStatusById(Integer id) throws StatusNotFoundException {
        return statusRepository.findById(id).orElseThrow(StatusNotFoundException::new);
    }

    private void shiftStatuses(Project project) {
        List<Status> allStatuses = getAllStatusesOfProject(project);
        allStatuses.forEach(status -> status.setOrderNr(status.getOrderNr() + 1));

        statusRepository.saveAll(allStatuses);
    }

    public Status createStatus(Project project, String statusName, String backgroundColor) {
        this.shiftStatuses(project);
        return statusRepository.save(new Status(project, statusName, backgroundColor, 0));
    }

    public Status createStatusWithOrder(Project project, String statusName, String backgroundColor, int order) {
        return statusRepository.save(new Status(project, statusName, backgroundColor, order));
    }

    public Status getStatusByStatusName(Project project, String statusName) throws StatusNotFoundException {
        return statusRepository.findByStatusNameAndProject(statusName, project).orElseThrow(StatusNotFoundException::new);
    }

    public void deleteStatusWithBugs(Integer statusId) throws StatusNotFoundException {
        Status statusToDelete = getStatusById(statusId);
        statusRepository.delete(statusToDelete);
    }

    public void updateStatusName(Integer oldStatusId, String newStatusName) throws StatusNotFoundException {
        Status statusToUpdate = getStatusById(oldStatusId);
        statusToUpdate.setStatusName(newStatusName);
        statusRepository.save(statusToUpdate);
    }

    public void updateStatusColor(Integer statusId, String newStatusColor) throws StatusNotFoundException {
        Status statusToUpdate = getStatusById(statusId);
        statusToUpdate.setStatusColor(newStatusColor);
        statusRepository.save(statusToUpdate);
    }

    public void reorderStatusesByChangedStatusOrder(Project project, int oldOrder, int newOrder) throws StatusNotFoundException {
        Status statusOrderChanged = this.statusRepository.findByOrderNrAndProject(oldOrder, project);

        List<Status> allStatuses = getAllStatusesOfProject(project);

        if (newOrder < oldOrder) {
            for (int i = newOrder; i < oldOrder; i++) {
                Status currentStatus = statusRepository.findByOrderNrAndProject(i, project);
                currentStatus.incrementOrderNr();
            }
        }

        if (newOrder > oldOrder) {
            for (int i = oldOrder + 1; i <= newOrder; i++) {
                Status currentStatus = statusRepository.findByOrderNrAndProject(i, project);
                currentStatus.decrementOrderNr();
            }
        }

        statusRepository.saveAll(allStatuses);

        statusOrderChanged.setOrderNr(newOrder);
        statusRepository.save(statusOrderChanged);
    }

    public Status getClosedStatusIfExists(Project project) throws NoClosedStatusException {
        Status closedStatus = getAllStatusesOfProject(project).stream()
                .filter(status -> status.getStatusName().equalsIgnoreCase("Closed"))
                .collect(Collectors.toList())
                .get(0);

        if (closedStatus != null) {
            return closedStatus;
        } else {
            throw new NoClosedStatusException();
        }
    }
}
