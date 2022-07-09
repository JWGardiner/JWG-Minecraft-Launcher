package com.jwg;

import com.jwg.jwgapi.errorHandler;
import com.jwg.jwgapi.logger;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jwg.launcher.LauncherKt.start;
import static com.jwg.Main.*;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static java.lang.System.exit;

public class clientInstaller {
    public static void vanilla() throws IOException {
        URL versions = new URL("https://raw.githubusercontent.com/JWGardiner/JWG-Minecraft-Launcher/main/minecraft/vanilla/versions");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(versions.openStream()));
        URL versionsDownload = new URL("https://raw.githubusercontent.com/JWGardiner/JWG-Minecraft-Launcher/main/minecraft/vanilla/versiondownload");
        BufferedReader in2 = new BufferedReader(
                new InputStreamReader(versionsDownload.openStream()));
        String versionLine;
        while ((versionLine = in.readLine()) != null)  {
            //Create all the folders
            String clientJarDownload = in2.readLine();
            try {
                File file = new File("launcher/templates/"+versionLine);
                if (!file.exists()) {
                    try {
                        Path path = Paths.get("launcher/templates/"+versionLine);
                        Files.createDirectories(path);

                    } catch (IOException e) {
                        errorHandler.handleError(e+ " Fatal Crash", "JWG MC Pre-Init", versionInt(version), logFile);
                        exit(0);
                    }
                }

            } catch(Exception e) {
                errorHandler.handleError("Couldn't update: No connection? Starting anyway..", "JWG MC Pre-Init", versionInt(version), logFile);
                start();
            }

            //Download the client jar
            File clientJarFolder = new File("launcher/templates/"+versionLine+"/client.jar");
            if (!clientJarFolder.exists()) {
                logger.log(logFile, versionInt(version), project, 0, "Downloading Client jar: " + clientJarDownload);
                URL website = new URL(clientJarDownload);
                ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                FileOutputStream fos = new FileOutputStream("launcher/vanilla/" + versionLine + "/client.jar");
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            }
        }
        in.close();
        in2.close();
    }
}