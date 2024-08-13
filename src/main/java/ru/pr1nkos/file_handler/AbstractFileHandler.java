package ru.pr1nkos.file_handler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The type Abstract file handler.
 */
abstract class AbstractFileHandler implements FileHandler {

    @Override
    public List<String[]> readFile(String fileName) throws IOException {
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader reader = getBufferedReader(fileName)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\";\"") || line.startsWith("\"")) {
                    String[] parts = line.split("\";\"");
                    for (int i = 0; i < parts.length; i++) {
                        parts[i] = parts[i].replace("\"", "").trim();
                    }
                    lines.add(parts);
                }
            }
        }
        return lines;
    }

    @Override
    public void writeFile(String fileName, Map<String, Set<String>> groups) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            List<Map.Entry<String, Set<String>>> sortedGroups = new ArrayList<>(groups.entrySet());
            sortedGroups.sort((a, b) -> b.getValue().size() - a.getValue().size());

            int multiElementGroups = 0;
            for (Map.Entry<String, Set<String>> entry : sortedGroups) {
                if (entry.getValue().size() > 1) {
                    multiElementGroups++;
                }
            }

            writer.write("Количество групп с более чем одним элементом: " + multiElementGroups + "\n\n");

            int groupNumber = 1;
            for (Map.Entry<String, Set<String>> entry : sortedGroups) {
                writer.write("Group " + groupNumber + "\n");
                for (String line : entry.getValue()) {
                    writer.write(line + "\n");
                }
                writer.write("\n");
                groupNumber++;
            }
        }
    }

    /**
     * Gets buffered reader.
     *
     * @param fileName the file name
     * @return the buffered reader
     * @throws IOException the io exception
     */
    protected abstract BufferedReader getBufferedReader(String fileName) throws IOException;


}