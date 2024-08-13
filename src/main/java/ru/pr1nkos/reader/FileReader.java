package ru.pr1nkos.reader;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * The interface File reader.
 */
public interface FileReader {
    /**
     * Read file buffered reader.
     *
     * @param filePath the file path
     * @return the buffered reader
     * @throws IOException the io exception
     */
    BufferedReader readFile(String filePath) throws IOException;
}