package com.jwg.jwgapi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class readFile {
    public static String fileRead(String filePath) throws IOException {
        return Files.readString(Paths.get(filePath));
    }
    public static String fileReadLine(String filepath, int line) throws IOException {
        String file = Files.readAllLines(Paths.get(filepath)).get(line);
        return file;
    }
}