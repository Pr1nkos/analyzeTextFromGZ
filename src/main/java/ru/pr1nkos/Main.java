package ru.pr1nkos;

import ru.pr1nkos.processors.lineprocessor.ConcurrentGraphLineProcessor;
import ru.pr1nkos.processors.fileprocessors.ConcurrentFileProcessor;
import ru.pr1nkos.processors.lineprocessor.LineProcessor;
import ru.pr1nkos.reader.FileReader;
import ru.pr1nkos.reader.GzipFileReader;
import ru.pr1nkos.writer.DefaultFileWriter;
import ru.pr1nkos.writer.FileWriter;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        String inputFile;
        if (args.length < 1) {
            System.out.println("Использование: java -jar analyzeTextFromGZ.jar {название архива с txt}.gz");
            return;
        }
        inputFile = "src/main/resources/input/" + args[0];
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        LineProcessor lineProcessor = new ConcurrentGraphLineProcessor();
        FileWriter fileWriter = new DefaultFileWriter();
        FileReader fileReader = new GzipFileReader();

        ConcurrentFileProcessor processor = new ConcurrentFileProcessor(lineProcessor, fileWriter, fileReader, executor);
        processor.processFile(inputFile, "src/main/resources/output/output.txt", executor);

        try {
            executor.shutdown();
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        lineProcessor.shutdown();

        long endTime = System.currentTimeMillis();
        System.out.println("\nВремя работы программы: " + (endTime - startTime) + " мс");
    }
}