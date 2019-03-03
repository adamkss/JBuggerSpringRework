package com.adam.kiss.jbugger.services;

import com.adam.kiss.jbugger.entities.StatisticOutputDataWithColor;
import com.adam.kiss.jbugger.repositories.BugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final LabelService labelService;

    public List<StatisticOutputDataWithColor> getAssociatedNumberOfBugsForLabels() {
        return labelService.getAllLabels().stream()
                .filter(label -> label.getBugsWithThisLabel().size() != 0)
                .map(label -> new StatisticOutputDataWithColor(
                        label.getLabelName(),
                        label.getBugsWithThisLabel().size(),
                        label.getBackgroundColor()))
                .collect(Collectors.toList());
    }
}
