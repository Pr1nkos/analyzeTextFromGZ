package ru.pr1nkos.processors.lineprocessor;

import java.util.List;
import java.util.Set;

public interface LineProcessor {
    void processLine(String line);
    List<Set<String>> getGroups();
    default void shutdown() {}
}