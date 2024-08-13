package ru.pr1nkos.processors.lineprocessor;

import java.util.*;
import java.util.concurrent.*;


class DefaultLineProcessor implements LineProcessor {
    private final ConcurrentMap<String, Set<String>> columnValueGroupMap = new ConcurrentHashMap<>();
    private final ConcurrentMap<Set<String>, Set<String>> groups = new ConcurrentHashMap<>();

    @Override
    public void processLine(String line) {
        String[] columns = line.split(";");

        Set<Set<String>> foundGroups = findGroups(columns);

        Set<String> targetGroup = determineTargetGroup(foundGroups);

        mergeFoundGroups(foundGroups, targetGroup);

        targetGroup.add(line);

        updateColumnValueGroupMap(columns, targetGroup);
    }

    private Set<Set<String>> findGroups(String[] columns) {
        Set<Set<String>> foundGroups = ConcurrentHashMap.newKeySet();

        for (String value : columns) {
            if (!value.isEmpty()) {
                String sanitizedValue = sanitizeValue(value);
                Set<String> groupSet = columnValueGroupMap.get(sanitizedValue);
                if (groupSet != null) {
                    foundGroups.add(groupSet);
                }
            }
        }

        return foundGroups;
    }

    private String sanitizeValue(String value) {
        return value.replace("\"", "").trim();
    }

    private Set<String> determineTargetGroup(Set<Set<String>> foundGroups) {
        if (foundGroups.isEmpty()) {
            Set<String> newGroup = ConcurrentHashMap.newKeySet();
            groups.put(newGroup, newGroup);
            return newGroup;
        }
        else {
            return foundGroups.iterator().next();
        }
    }

    private void mergeFoundGroups(Set<Set<String>> foundGroups, Set<String> targetGroup) {
        for (Set<String> group : foundGroups) {
            if (!group.equals(targetGroup)) {
                mergeGroup(group, targetGroup);
                removeGroupFromMappings(group);
            }
        }
    }

    private void mergeGroup(Set<String> sourceGroup, Set<String> targetGroup) {
        targetGroup.addAll(sourceGroup);
        groups.remove(sourceGroup);
    }

    private void removeGroupFromMappings(Set<String> group) {
        for (String val : group) {
            columnValueGroupMap.compute(val, (k, v) -> {
                if (v != null && v.isEmpty()) {
                    return null;
                }
                return v;
            });
        }
    }

    private void updateColumnValueGroupMap(String[] columns, Set<String> targetGroup) {
        for (String value : columns) {
            if (!value.isEmpty()) {
                String sanitizedValue = sanitizeValue(value);
                columnValueGroupMap.compute(sanitizedValue, (k, v) -> {
                    if (v == null) v = ConcurrentHashMap.newKeySet();
                    v.addAll(targetGroup);
                    return v;
                });
            }
        }
    }

    @Override
    public List<Set<String>> getGroups() {
        return new ArrayList<>(groups.keySet());
    }
}
