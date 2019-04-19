package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewProjectShortOutDto {
    private int id;
    private String name;

    public static ViewProjectShortOutDto mapToDto(Project project) {
        return ViewProjectShortOutDto.builder()
                .id(project.getId())
                .name(project.getName())
                .build();
    }

    public static List<ViewProjectShortOutDto> mapToDtoList(List<Project> bugList) {
        return bugList.stream().map(ViewProjectShortOutDto::mapToDto).collect(Collectors.toList());
    }
}

