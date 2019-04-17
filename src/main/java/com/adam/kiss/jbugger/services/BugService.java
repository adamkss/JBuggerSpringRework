package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.dtos.ClosedStatisticsAboutBugDtoOut;
import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.exceptions.BugNotFoundException;
import com.adam.kiss.jbugger.exceptions.NoClosedStatusException;
import com.adam.kiss.jbugger.repositories.BugRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BugService {
    private final BugRepository bugRepository;
    private final AttachmentService attachmentService;
    private final StatusService statusService;
    private final NotificationService notificationService;

    public List<Bug> getAllBugs(String filter) {
        return bugRepository.findAllFiltered(filter);
    }

    public Bug getBugById(Integer id) throws BugNotFoundException {
        return bugRepository.findById(id).orElseThrow(BugNotFoundException::new);
    }

    public Bug createBug(Bug bug) {
        bugRepository.save(bug);
        attachmentService.associateBugToAttachments(bug, bug.getAttachments());
        return bug;
    }

    public void updateBugStatus(Integer bugId, Status status) throws BugNotFoundException {
        Optional<Bug> bugToUpdateStatus = bugRepository.findById(bugId);
        Bug bugToUpdate = bugToUpdateStatus.orElseThrow(BugNotFoundException::new);
        bugToUpdate.setStatus(status);
        bugRepository.save(bugToUpdate);
        notificationService.sendNotificationUsersBugStatusWasUpdated(bugToUpdate.getCreatedBy(), bugToUpdate);
    }

    public Bug updateBug(Bug bugToUpdate) {
        notificationService.sendNotificationUsersBugWasUpdated(bugToUpdate.getCreatedBy(), bugToUpdate);
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

    public List<ChangeInBug> getAllStatusChangesOfABug(Integer bugId) throws BugNotFoundException {
        return bugRepository
                .findById(bugId).orElseThrow(BugNotFoundException::new)
                .getChangesOfBug()
                .stream()
                .filter(changeInBug ->
                {
                    if (changeInBug.getFieldChanged() == null)
                        return false;
                    return changeInBug.getFieldChanged().equalsIgnoreCase("STATUS");
                })
                .collect(Collectors.toList());
    }

    public void assignBugToUser(Bug bug, User user) {
        bug.setAssignedTo(user);
        bugRepository.save(bug);
    }

    public void deleteBug(Integer bugId) {
        bugRepository.deleteById(bugId);
    }

    public void closeBug(Integer bugId) throws BugNotFoundException, NoClosedStatusException {
        Bug bugToClose = getBugById(bugId);
        bugToClose.setStatus(
                statusService.getClosedStatusIfExists()
        );
        bugRepository.save(bugToClose);
        notificationService.sendNotificationUsersBugWasClosed(bugToClose.getCreatedBy(), bugToClose);
    }

    public ClosedStatusStatistics calculateAverageCloseTimes() {
        List<ClosedBugInfo> closedBugsInfo = new ArrayList<>();

        getAllBugs("").forEach(bug -> {
            List<ChangeInBug> changesInBug = bug.getChangesOfBug()
                    .stream()
                    .filter(changeInBug -> {
                        if (changeInBug.getFieldChanged() == null)
                            return false;
                        return changeInBug.getFieldChanged().equalsIgnoreCase("STATUS");
                    })
                    .collect(Collectors.toList());

            List<ChangeInBug> closedChanges = changesInBug
                    .stream()
                    .filter(change -> change.getNewValue().equalsIgnoreCase("CLOSED"))
                    .collect(Collectors.toList());

            if (!closedChanges.isEmpty()) {
                ChangeInBug changeInBugClosing = closedChanges.get(0);
                Duration duration = Duration.between(
                        bug.getCreatedTime(),
                        changeInBugClosing.getTimeOfChangeHappening()
                );
                closedBugsInfo.add(
                        new ClosedBugInfo(
                                bug,
                                bug.getCreatedTime(),
                                changeInBugClosing.getTimeOfChangeHappening(),
                                changeInBugClosing.getChangeAuthor(),
                                duration
                        )
                );

            }
        });

        long totalDays = closedBugsInfo
                .stream().map(ClosedBugInfo::getDuration).mapToLong(Duration::toDays).sum();
        long totalHours = closedBugsInfo
                .stream().map(ClosedBugInfo::getDuration).mapToLong(Duration::toHours).sum();
        long totalMinutes = closedBugsInfo
                .stream().map(ClosedBugInfo::getDuration).mapToLong(Duration::toMinutes).sum();

        long averageDays;
        long averageHours;
        long averageMinutes;
        if (closedBugsInfo.isEmpty()) {
            averageDays = 0;
            averageHours = 0;
            averageMinutes = 0;
        } else {
            averageDays = totalDays / closedBugsInfo.size();
            averageHours = totalHours / closedBugsInfo.size();
            averageMinutes = totalMinutes / closedBugsInfo.size();
        }

        return new ClosedStatusStatistics(
                ClosedStatisticsAboutBugDtoOut.mapToDtoList(closedBugsInfo),
                totalDays,
                totalHours,
                totalMinutes,
                averageMinutes,
                averageHours,
                averageDays
        );
    }

    @Data
    @AllArgsConstructor
    public static class ClosedBugInfo {
        private Bug bug;
        private LocalDateTime createdTime;
        private LocalDateTime closedTime;
        private User closedByUser;
        private Duration duration;
    }
}
