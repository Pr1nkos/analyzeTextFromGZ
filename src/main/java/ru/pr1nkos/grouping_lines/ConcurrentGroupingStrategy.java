package ru.pr1nkos.grouping_lines;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The type Concurrent grouping strategy.
 */
public class ConcurrentGroupingStrategy extends AbstractGroupingStrategy {

    @Override
    public Map<String, Set<String>> groupLines(List<String[]> lines) {
        Map<String, Set<String>> groups = new ConcurrentHashMap<>();
        Map<String, String> lineToGroup = new ConcurrentHashMap<>();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        try {
            for (String[] line : lines) {
                executorService.submit(() -> {
                    String groupKey = findGroupKey(line, lineToGroup);

                    if (groupKey == null) {
                        groupKey = UUID.randomUUID().toString();
                    }

                    for (String value : line) {
                        if (!value.isEmpty()) {
                            lineToGroup.put(value, groupKey);
                        }
                    }

                    groups.computeIfAbsent(groupKey, k -> new ConcurrentHashSet<>()).add(Arrays.toString(line));
                });
            }
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
            }
        }

        return groups;
    }

    @Override
    protected String findGroupKey(String[] line, Map<String, String> lineToGroup) {
        for (String value : line) {
            if (!value.isEmpty() && lineToGroup.containsKey(value)) {
                return lineToGroup.get(value);
            }
        }
        return null;
    }

    /**
     * The type Concurrent hash set.
     *
     * @param <E> the type parameter
     */
    public static class ConcurrentHashSet<E> extends AbstractSet<E> {
        private final ConcurrentHashMap<E, Boolean> map;

        /**
         * Instantiates a new Concurrent hash set.
         */
        public ConcurrentHashSet() {
            map = new ConcurrentHashMap<>();
        }

        @Override
        public boolean add(E e) {
            return map.putIfAbsent(e, Boolean.TRUE) == null;
        }

        @Override
        public boolean contains(Object o) {
            return map.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            return map.remove(o, Boolean.TRUE);
        }

        @Override
        public Iterator<E> iterator() {
            return map.keySet().iterator();
        }

        @Override
        public int size() {
            return map.size();
        }
    }
}