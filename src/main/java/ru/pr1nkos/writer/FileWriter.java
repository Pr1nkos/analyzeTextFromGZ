package ru.pr1nkos.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * The interface File writer.
 */
public interface FileWriter {
    /**
     * Write groups to file.
     *
     * @param groups         the groups
     * @param outputFilePath the output file path
     */
    void writeGroupsToFile(List<Set<String>> groups, String outputFilePath);

    /**
     * Write groups int.
     *
     * @param groups the groups
     * @param writer the writer
     * @return the int
     * @throws IOException the io exception
     */
    int writeGroups(List<Set<String>> groups, BufferedWriter writer) throws IOException;
}