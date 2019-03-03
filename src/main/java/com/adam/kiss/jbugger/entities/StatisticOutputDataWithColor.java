package com.adam.kiss.jbugger.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticOutputDataWithColor {
    public String title;
    public Integer value;
    public String color;
}
