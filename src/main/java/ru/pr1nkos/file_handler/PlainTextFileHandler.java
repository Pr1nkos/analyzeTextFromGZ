package ru.pr1nkos.file_handler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The type Plain text file handler.
 */
public class PlainTextFileHandler extends AbstractFileHandler {

    @Override
    protected BufferedReader getBufferedReader(String fileName) throws IOException {
        return new BufferedReader(new FileReader(fileName));
    }
}