package com.adam.kiss.jbugger.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ProjectActiveBugsStatistic {
   private LocalDateTime dateTime;
   private Integer numberOfActiveBugs;
}
