package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStatusDtoIn {
    private String statusName;
    private String statusColor;
}
