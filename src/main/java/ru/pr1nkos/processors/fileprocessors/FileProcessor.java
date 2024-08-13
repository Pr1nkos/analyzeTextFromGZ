package ru.pr1nkos.processors.fileprocessors;


import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * The interface File processor.
 */
public interface FileProcessor {
    /**
     * Process file.
     *
     * @param filePath       the file path
     * @param outputFilePath the output file path
     * @param executor       the executor
     * @throws IOException the io exception
     */
    void processFile(String filePath, String outputFilePath, ExecutorService executor) throws IOException;
}
