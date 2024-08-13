package ru.pr1nkos.processors.lineprocessor;

import java.util.List;
import java.util.Set;

/**
 * The interface Line processor.
 */
public interface LineProcessor {
    /**
     * Process line.
     *
     * @param line the line
     */
    void processLine(String line);

    /**
     * Gets groups.
     *
     * @return the groups
     */
    List<Set<String>> getGroups();

    default void shutdown() {}
}