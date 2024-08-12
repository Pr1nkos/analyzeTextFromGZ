package ru.pr1nkos;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.zip.GZIPInputStream;

public class FileProcessor {
    private final ExecutorService executor;

    public FileProcessor(ExecutorService executor) {
        this.executor = executor;
    }

    public void processFile(String filePath, String outputFilePath) {
        Set<String[]> uniqueLines = Collections.synchronizedSet(new HashSet<>());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new GZIPInputStream(new FileInputStream(filePath))))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String finalLine = line.trim();
                String[] columns = finalLine.split(";");
                CompletableFuture.runAsync(() -> uniqueLines.add(columns), executor);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();

        List<Set<String[]>> groups = groupLines(uniqueLines);
        writeGroupsToFile(groups, outputFilePath);
    }


    private List<Set<String[]>> groupLines(Set<String[]> lines) {
        List<Set<String[]>> groups = new ArrayList<>();
        Map<String, Set<String[]>> columnValueGroupMap = new HashMap<>();

        for (String[] line : lines) {

            Set<Set<String[]>> foundGroups = new HashSet<>();

            for (String s : line) {
                String value = s.replace("\"", "").trim();
                if (!value.isEmpty() && columnValueGroupMap.containsKey(value)) {
                    foundGroups.add(columnValueGroupMap.get(value));
                }
            }

            Set<String[]> targetGroup;
            if (foundGroups.isEmpty()) {

                targetGroup = new HashSet<>();
                groups.add(targetGroup);
            }
            else {
                targetGroup = foundGroups.iterator().next();
                for (Set<String[]> group : foundGroups) {
                    if (group != targetGroup) {
                        targetGroup.addAll(group);
                        groups.remove(group);
                    }
                }
            }
            targetGroup.add(line);

            for (String s : line) {
                String value = s.replace("\"", "").trim();
                if (!value.isEmpty()) {
                    columnValueGroupMap.put(value, targetGroup);
                }
            }
        }
        return groups;
    }

    private void writeGroupsToFile(List<Set<String[]>> groups, String outputFilePath) {
        try (FileWriter writer = new FileWriter(outputFilePath)) {
            groups.sort((g1, g2) -> Integer.compare(g2.size(), g1.size()));

            int groupCount = 0;
            for (Set<String[]> group : groups) {
                if (group.size() > 1) {
                    groupCount++;
                    writer.write("Группа " + groupCount + "\n");
                    for (String[] line : group) {
                        writer.write(String.join(";", line) + "\n");
                    }
                    writer.write("\n");
                }
            }

            writer.write("Total number of groups with more than one element: " + groupCount + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}