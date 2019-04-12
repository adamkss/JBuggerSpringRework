package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Label;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewLabelDtoOut {
    private int id;

    private String labelName;

    private String backgroundColor;

    public static ViewLabelDtoOut mapLabelToDto(Label label) {
        return ViewLabelDtoOut.builder()
                .id(label.getId())
                .labelName(label.getLabelName())
                .backgroundColor(label.getBackgroundColor())
                .build();
    }

    public static List<ViewLabelDtoOut> mapLabelListToDtoList(List<Label> labels) {
        return labels.stream()
                .map(ViewLabelDtoOut::mapLabelToDto)
                .collect(Collectors.toList());
    }
}

