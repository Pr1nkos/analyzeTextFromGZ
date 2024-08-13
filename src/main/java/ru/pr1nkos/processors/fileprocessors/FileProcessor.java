package ru.pr1nkos.processors.fileprocessors;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

public interface FileProcessor {
    void processFile(String filePath, String outputFilePath, ExecutorService executor) throws IOException;
}