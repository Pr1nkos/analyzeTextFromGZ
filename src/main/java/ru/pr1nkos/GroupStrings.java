package ru.pr1nkos;

import ru.pr1nkos.file_handler.FileHandler;
import ru.pr1nkos.file_handler.GZipFileHandler;
import ru.pr1nkos.file_handler.PlainTextFileHandler;
import ru.pr1nkos.grouping_lines.DefaultGroupingStrategy;
import ru.pr1nkos.grouping_lines.GroupingStrategy;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * The type Group strings.
 */
public class GroupStrings {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String inputFile = "src/main/resources/input/" + args[0];
        String outputFile = "src/main/resources/output/output.txt";

        FileHandler fileHandler;

        try {
            if (inputFile.endsWith(".gz")) {
                fileHandler = new GZipFileHandler();
            }
            else {
                fileHandler = new PlainTextFileHandler();
            }

            List<String[]> lines = fileHandler.readFile(inputFile);

            GroupingStrategy groupingStrategy = new DefaultGroupingStrategy();
            Map<String, Set<String>> groups = groupingStrategy.groupLines(lines);

            fileHandler.writeFile(outputFile, groups);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}