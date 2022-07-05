package com.jwg;

import com.jwg.jwgapi.*;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jwg.Main.*;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static com.jwg.jwgapi.swingUtils.configureJframe;
import static java.lang.System.exit;

public class init {
    static boolean needsSetup = false;
    static boolean hasWifiConnection = false;
    static boolean updateModloader = false;

    public static void preInit() {
        if(!sysinfo.getOs().equals("linux") && !sysinfo.getOs().equals("windows")) {
            errorHandler.handleError("Unsupported OS: The program might still work. Proceed with caution. Logs from your installation are invalid.", "JWG MC Pre-Init", versionInt(version), logFile);
            logger.log(logFile, versionInt(version), project, 2, "LOGS ARE INVALID: UNSUPPORTED OS");
        }
        if(!new File("launcher").isDirectory()) {
            needsSetup = true;
        }
        try {
            URL checkConnectionGoogle = new URL("http://www.google.com");
            URLConnection connection = checkConnectionGoogle.openConnection();
            connection.connect();
            logger.log(logFile, versionInt(version), project, 0, "User is connected to WiFi.");
            hasWifiConnection = true;
        } catch (IOException e) {
            logger.log(logFile, versionInt(version), project, 1, "User is not connected to WiFi. - Or Google is down.");
        }
        if (needsSetup && !hasWifiConnection) {
            try {
                URL checkConnectionGit = new URL("http://www.github.com");
                URLConnection connection = checkConnectionGit.openConnection();
                connection.connect();
                logger.log(logFile, versionInt(version), project, 0, "User is connected to WiFi. - Google is down.");
                hasWifiConnection = true;
            } catch (IOException e) {
                logger.log(logFile, versionInt(version), project, 1, "User is not connected to WiFi. - Or Google & GitHub are down.");
                errorHandler.handleError("The launcher may be unable to auto-install; it cannot access google.com or github.com. Please check your WiFi connection - It will continue but may crash or have unpredictable behaviour without a WiFi connection! ", "JWG MC Pre-Init", versionInt(version), logFile);
            }
        }
    }
    public static void Init() {
        if (needsSetup) {
            try {
                autoSetup.setup();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            String modloaderUpdate = readFile.fileRead("modloaderUpdate.cfg");
            if (modloaderUpdate.equals("autoUpdate=true")) {
                updateModloader = true;
            }
        } catch (IOException e) {
            errorHandler.handleError(e+ " Fatal Crash", "JWG MC Pre-Init", versionInt(version), logFile);
            exit(0);
        }
        if (updateModloader) {
            try {
                Path path = Paths.get("launcher/templates/fabric");
                Files.createDirectories(path);
                logger.log(logFile, versionInt(version), project, 0, "Created templates/fabric directory.");
            } catch (IOException e) {
                logger.log(logFile, versionInt(version), project, 0, "Could not create template dir; does it already exist?");
            }
            try {
                Path path = Paths.get("launcher/templates/forge");
                Files.createDirectories(path);
                logger.log(logFile, versionInt(version), project, 0, "Created templates/forge directory.");
            } catch (IOException e) {
                logger.log(logFile, versionInt(version), project, 0, "Could not create template dir; does it already exist?");
            }
            try {
                Path path = Paths.get("launcher/templates/quilt");
                Files.createDirectories(path);
                logger.log(logFile, versionInt(version), project, 0, "Created templates/quilt directory.");
            } catch (IOException e) {
                logger.log(logFile, versionInt(version), project, 0, "Could not create template dir; does it already exist?");
            }
        }
        String popupString = null;
        try {
            popupString = readFile.fileReadLine("settings.cfg", 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        popupString = popupString.substring(11);
        boolean showPopup = !popupString.equals("true");
        try {
            if (!showPopup) {
                JOptionPane.showMessageDialog(null, "About to check for Minecraft updates - This could take a minute.");
            }
            clientInstaller.vanilla();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}