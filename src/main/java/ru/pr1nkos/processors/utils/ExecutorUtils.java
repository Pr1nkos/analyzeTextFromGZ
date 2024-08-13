package ru.pr1nkos.processors.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Executor utils.
 */
public class ExecutorUtils {

    /**
     * Shutdown executor service gracefully.
     *
     * @param executorService the executor service
     * @param timeout         the timeout
     * @param timeUnit        the time unit
     */
    public static void shutdownAndAwaitTermination(ExecutorService executorService, long timeout, TimeUnit timeUnit) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(timeout, timeUnit)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(timeout, timeUnit)) {
                    System.err.println("Executor service did not terminate");
                }
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
