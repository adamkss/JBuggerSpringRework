package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.ChangeInBug;
import com.adam.kiss.jbugger.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import utils.FormatHelper;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewChangeInBugDtoOut {
    private int id;

    private String changeText;

    private String timeOfChangeHappening;

    private ViewUserShortDtoOut changeAuthor;

    private boolean isFieldRelatedChange;

    private String fieldChanged;

    private String oldValue;

    private String newValue;

    public static ViewChangeInBugDtoOut mapChangeInBugToDto(ChangeInBug changeInBug) {
        ViewChangeInBugDtoOutBuilder viewChangeInBugDtoOutBuilder = ViewChangeInBugDtoOut.builder()
                .id(changeInBug.getId())
                .changeText(changeInBug.getChangeText())
                .timeOfChangeHappening(
                        FormatHelper.formatLocalDateTime(
                                changeInBug.getTimeOfChangeHappening()
                        )
                )
                .changeAuthor(
                        ViewUserShortDtoOut.mapToDto(
                                changeInBug.getChangeAuthor()
                        )
                )
                .fieldChanged(changeInBug.getFieldChanged())
                .oldValue(changeInBug.getOldValue())
                .newValue(changeInBug.getNewValue())
                .isFieldRelatedChange(changeInBug.isFieldRelatedChange());

        if (changeInBug.isFieldRelatedChange()) {
            viewChangeInBugDtoOutBuilder.fieldChanged = changeInBug.getFieldChanged();
            viewChangeInBugDtoOutBuilder.oldValue = changeInBug.getOldValue();
            viewChangeInBugDtoOutBuilder.newValue = changeInBug.getNewValue();
        }
        return viewChangeInBugDtoOutBuilder.build();
    }
}

