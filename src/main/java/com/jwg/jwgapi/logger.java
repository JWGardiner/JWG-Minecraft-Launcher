package com.jwg.jwgapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Scanner;

public class logger {
    public static void StartLogger(String logFile) {
        File LogFile = new File(logFile);
        if (LogFile.exists()) {
            try {
                Files.delete(Path.of(logFile));
            } catch (IOException x) {
                System.err.println(x);
            }
        }
        try {
            if (LogFile.createNewFile()) {
                System.out.println("[LOGGER] (jwgapi) Starting Logger...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void log(String logFile, int version, String name, int severity, String logMessage) {
        String[] logSeverity = {"[INFO]","[WARN]","[ERR]","[ERR/FATAL]","[NULL]"};
        if (severity >= 4) {
            severity = 4;
        }
        String log = "";
        String logString = logSeverity[severity] + " (" + name + ", " + version + ") " + logMessage;

        try {
            log = new String(Files.readAllBytes(Paths.get(logFile)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            FileWriter logWriter = new FileWriter(logFile);
            logWriter.write(log + "\n" + logString);
            logWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(logString);
    }
}