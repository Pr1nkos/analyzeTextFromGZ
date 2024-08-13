package ru.pr1nkos.processors.fileprocessors;


import ru.pr1nkos.processors.lineprocessor.LineProcessor;
import ru.pr1nkos.reader.FileReader;
import ru.pr1nkos.writer.FileWriter;

import java.io.BufferedReader;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * The type Abstract file processor.
 */
public abstract class AbstractFileProcessor implements FileProcessor {
    /**
     * The Line processor.
     */
    protected final LineProcessor lineProcessor;
    /**
     * The File writer.
     */
    protected final FileWriter fileWriter;
    /**
     * The File reader.
     */
    protected final FileReader fileReader;

    /**
     * Instantiates a new Abstract file processor.
     *
     * @param lineProcessor the line processor
     * @param fileWriter    the file writer
     * @param fileReader    the file reader
     */
    public AbstractFileProcessor(LineProcessor lineProcessor, FileWriter fileWriter, FileReader fileReader) {
        this.lineProcessor = lineProcessor;
        this.fileWriter = fileWriter;
        this.fileReader = fileReader;
    }

    @Override
    public void processFile(String inputFilePath, String outputFilePath, ExecutorService executor) throws IOException {
        try (BufferedReader reader = fileReader.readFile(inputFilePath)) {
            List<String> lines = readLines(reader);

            List<Future<?>> futures = submitTasks(lines, executor);

            waitForCompletion(futures);

            List<Set<String>> groups = lineProcessor.getGroups();
            fileWriter.writeGroupsToFile(groups, outputFilePath);

        } finally {
            shutdownExecutor();
        }
    }

    private List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }
        return lines;
    }

    private List<Future<?>> submitTasks(List<String> lines, ExecutorService executor) {
        List<Future<?>> futures = new ArrayList<>();
        for (String line : lines) {
            futures.add(executor.submit(() -> lineProcessor.processLine(line)));
        }
        return futures;
    }

    private void waitForCompletion(List<Future<?>> futures) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shutdown executor.
     */
    protected abstract void shutdownExecutor();
}