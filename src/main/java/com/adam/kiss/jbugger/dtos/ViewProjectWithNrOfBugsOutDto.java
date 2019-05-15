package com.adam.kiss.jbugger.dtos;

import com.adam.kiss.jbugger.entities.Project;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ViewProjectWithNrOfBugsOutDto {
    private int id;
    private String name;
    private int nrOfBugs;

    public static ViewProjectWithNrOfBugsOutDto mapToDto(Project project) {
        return ViewProjectWithNrOfBugsOutDto.builder()
                .id(project.getId())
                .name(project.getName())
                .nrOfBugs(project.getBugs().size())
                .build();
    }

    public static List<ViewProjectWithNrOfBugsOutDto> mapToDtoList(List<Project> bugList) {
        return bugList.stream().map(ViewProjectWithNrOfBugsOutDto::mapToDto).collect(Collectors.toList());
    }
}

