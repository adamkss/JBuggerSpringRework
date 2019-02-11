package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewStatusDtoOut {
    private String statusName;

    public static ViewStatusDtoOut mapStatusToDTO(Status status) {
        return ViewStatusDtoOut
                .builder()
                .statusName(status.getStatusName())
                .build();
    }
}
