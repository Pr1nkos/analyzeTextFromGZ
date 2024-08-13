package ru.pr1nkos.processors.fileprocessors;


import ru.pr1nkos.processors.lineprocessor.LineProcessor;
import ru.pr1nkos.processors.utils.ExecutorUtils;
import ru.pr1nkos.reader.FileReader;
import ru.pr1nkos.writer.FileWriter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The type Concurrent file processor.
 */
public class ConcurrentFileProcessor extends AbstractFileProcessor {
    private final ExecutorService executor;

    /**
     * Instantiates a new Concurrent file processor.
     *
     * @param lineProcessor the line processor
     * @param fileWriter    the file writer
     * @param fileReader    the file reader
     * @param executor      the executor
     */
    public ConcurrentFileProcessor(LineProcessor lineProcessor, FileWriter fileWriter, FileReader fileReader, ExecutorService executor) {
        super(lineProcessor, fileWriter, fileReader);
        this.executor = executor;
    }

    @Override
    protected void shutdownExecutor() {
        ExecutorUtils.shutdownAndAwaitTermination(executor, 1, TimeUnit.HOURS);
    }
}
