package ru.pr1nkos.processors.lineprocessor;

import ru.pr1nkos.processors.utils.ExecutorUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentGraphLineProcessor extends GraphLineProcessor {
    private final ExecutorService graphExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private final AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void processLine(String line) {
        graphExecutor.submit(() -> {
            try {
                super.processLine(line);
            } finally {
                int processedCount = counter.incrementAndGet();
                System.out.printf("Lines processed: %d%n", processedCount);
            }
        });
    }

    @Override
    public void shutdown() {
        ExecutorUtils.shutdownAndAwaitTermination(graphExecutor, 1, TimeUnit.HOURS);
    }
}