package com.jwg.launcher;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class getInstallations {
    public static String[] getAllInstalls() {
        String[] installs = new String[100000];


        try (DirectoryStream<Path> stream = Files
                .newDirectoryStream(Paths.get("launcher/profiles"))) {
            int i = 0;
            for (Path path : stream) {
                installs[i] = String.valueOf(path);
                i = i+1;
            }
            return installs;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}