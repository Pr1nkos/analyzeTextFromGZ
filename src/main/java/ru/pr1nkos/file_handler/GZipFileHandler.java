package ru.pr1nkos.file_handler;

import java.io.*;
import java.util.zip.GZIPInputStream;

/**
 * The type G zip file handler.
 */
public class GZipFileHandler extends AbstractFileHandler {

    @Override
    protected BufferedReader getBufferedReader(String fileName) throws IOException {
        InputStream fileStream = new FileInputStream(fileName);
        InputStream gzipStream = new GZIPInputStream(fileStream);
        return new BufferedReader(new InputStreamReader(gzipStream));
    }
}