package ru.pr1nkos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        if (args.length < 1) {
            System.out.println("Usage: java -jar {название проекта}.jar тестовый-файл.txt.gz");
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        FileProcessor processor = new FileProcessor(executor);
        processor.processFile(args[0], "output.txt");

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }
}