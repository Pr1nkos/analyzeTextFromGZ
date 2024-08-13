package ru.pr1nkos.processors.lineprocessor;

import ru.pr1nkos.processors.utils.ExecutorUtils;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Concurrent graph line processor.
 */
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

    /**
     * Shutdown.
     */
    @Override
    public void shutdown() {
        ExecutorUtils.shutdownAndAwaitTermination(graphExecutor, 1, TimeUnit.HOURS);
    }
}
