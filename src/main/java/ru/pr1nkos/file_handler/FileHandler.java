package ru.pr1nkos.file_handler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The interface File handler.
 */
public interface FileHandler {
    /**
     * Read file list.
     *
     * @param fileName the file name
     * @return the list
     * @throws IOException the io exception
     */
    List<String[]> readFile(String fileName) throws IOException;

    /**
     * Write file.
     *
     * @param fileName the file name
     * @param groups   the groups
     * @throws IOException the io exception
     */
    void writeFile(String fileName, Map<String, Set<String>> groups) throws IOException;
}