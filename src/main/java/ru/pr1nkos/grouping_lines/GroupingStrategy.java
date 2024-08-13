package ru.pr1nkos.grouping_lines;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The interface Grouping strategy.
 */
public interface GroupingStrategy {
    /**
     * Group lines map.
     *
     * @param lines the lines
     * @return the map
     */
    Map<String, Set<String>> groupLines(List<String[]> lines);
}