package ru.pr1nkos.writer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class DefaultFileWriter implements FileWriter {

    @Override
    public void writeGroupsToFile(List<Set<String>> groups, String outputFilePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
            sortGroupsBySize(groups);
            int groupCount = writeGroups(groups, writer);
            writeGroupCount(writer, groupCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortGroupsBySize(List<Set<String>> groups) {
        groups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));
    }

    @Override
    public int writeGroups(List<Set<String>> groups, BufferedWriter writer) throws IOException {
        int groupCount = 0;
        for (Set<String> group : groups) {
            if (group.size() > 1) {
                groupCount++;
                writeGroup(writer, group, groupCount);
            }
        }
        return groupCount;
    }

    private void writeGroup(BufferedWriter writer, Set<String> group, int groupCount) throws IOException {
        writer.write("Группа " + groupCount + "\n");
        for (String line : group) {
            writer.write(line + "\n");
        }
        writer.write("\n");
    }

    private void writeGroupCount(BufferedWriter writer, int groupCount) throws IOException {
        writer.write("Общее количество групп, где больше 1 элемента:  " + groupCount + "\n");
    }
}