package ru.pr1nkos.grouping_lines;

import java.util.*;

/**
 * The type Abstract grouping strategy.
 */
abstract class AbstractGroupingStrategy implements GroupingStrategy {

    @Override
    public Map<String, Set<String>> groupLines(List<String[]> lines) {
        Map<String, Set<String>> groups = new HashMap<>();
        Map<String, String> lineToGroup = new HashMap<>();

        for (String[] line : lines) {
            String groupKey = findGroupKey(line, lineToGroup);

            if (groupKey == null) {
                groupKey = UUID.randomUUID().toString();
            }

            for (String value : line) {
                if (!value.isEmpty()) {
                    lineToGroup.put(value, groupKey);
                }
            }

            groups.computeIfAbsent(groupKey, k -> new HashSet<>()).add(Arrays.toString(line));
        }

        return groups;
    }

    /**
     * Find group key string.
     *
     * @param line        the line
     * @param lineToGroup the line to group
     * @return the string
     */
    protected abstract String findGroupKey(String[] line, Map<String, String> lineToGroup);
}