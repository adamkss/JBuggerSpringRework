package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewLabelDtoOut {
    private int id;

    private String labelName;

    public static ViewLabelDtoOut mapLabelToDto(Label label){
        return ViewLabelDtoOut.builder()
                .id(label.getId())
                .labelName(label.getLabelName())
                .build();
    }
}

