package ru.pr1nkos.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

public class GzipFileReader implements FileReader {
    @Override
    public BufferedReader readFile(String filePath) throws IOException {
        return new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath))));
    }
}