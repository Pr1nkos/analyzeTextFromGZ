package ru.pr1nkos.grouping_lines;

import java.util.Map;

/**
 * The type Default grouping strategy.
 */
public class DefaultGroupingStrategy extends AbstractGroupingStrategy {

    @Override
    protected String findGroupKey(String[] line, Map<String, String> lineToGroup) {
        for (String value : line) {
            if (!value.isEmpty() && lineToGroup.containsKey(value)) {
                return lineToGroup.get(value);
            }
        }
        return null;
    }
}