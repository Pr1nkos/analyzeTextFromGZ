package ru.pr1nkos.reader;

import java.io.BufferedReader;
import java.io.IOException;

public interface FileReader {
    BufferedReader readFile(String filePath) throws IOException;
}