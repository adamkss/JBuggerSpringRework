package com.adam.kiss.jbugger.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUserStatistics {
    private List<UserWithNumberDto> solvedMostBugs = new ArrayList<>();
    private List<UserWithNumberDto> solvedLeastBugs = new ArrayList<>();
    private List<UserWithNumberDto> createdMostBugs = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class UserWithNumberDto {
        private String username;
        private String name;
        private Integer number;
    }
}

