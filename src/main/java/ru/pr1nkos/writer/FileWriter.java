package ru.pr1nkos.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface FileWriter {
    void writeGroupsToFile(List<Set<String>> groups, String outputFilePath);
    int writeGroups(List<Set<String>> groups, BufferedWriter writer) throws IOException;
}