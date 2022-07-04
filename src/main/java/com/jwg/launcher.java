package com.jwg;

import com.jwg.jwgapi.*;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jwg.Main.*;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static com.jwg.jwgapi.swingUtils.configureJframe;
import static java.lang.System.exit;

public class launcher {
    static boolean needsSetup = false;
    static boolean hasWifiConnection = false;
    static boolean updateModloader = false;
    static JFrame frame = new JFrame();
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
                File checkUpdateModldr = new File("modloaderUpdate.cfg");
                if (checkUpdateModldr.createNewFile()) {
                    logger.log(logFile, versionInt(version), project, 0, "Created check modloader update file");
                } else {
                    logger.log(logFile, versionInt(version), project, 1, "Couldn't create modloader update file");
                }
            } catch (IOException e) {
                errorHandler.handleError(e+ " Fatal Crash", "JWG MC Pre-Init", versionInt(version), logFile);
                exit(0);
            }
            logger.log(logFile, versionInt(version), project, 0, "Starting automatic setup...");
            try {
                Path path = Paths.get("launcher/profiles");
                Files.createDirectories(path);
                logger.log(logFile, versionInt(version), project, 0, "Created profiles directory.");
            } catch (IOException e) {
                errorHandler.handleError(e + " Could not create launcher directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
                exit(0);
            }
            try {
                Path path = Paths.get("launcher/templates/vanilla");
                Files.createDirectories(path);
                logger.log(logFile, versionInt(version), project, 0, "Created templates/vanilla directory.");
            } catch (IOException e) {
                errorHandler.handleError(e + " Could not create template directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
                exit(0);
            }

            logger.log(logFile, versionInt(version), project, 0, "Created all directories!");
            configureJframe(frame, 700, 500, 500, 500, false, true);
            int reply = JOptionPane.showConfirmDialog(null, "Do you want to check for updated modloaders? (This only appears once, it's recommended to check for updated modloaders!)", "Update Modloaders?", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                writeFile.overwriteFile("modloaderUpdate.cfg", "autoUpdate=true");
            } else {
                writeFile.overwriteFile("modloaderUpdate.cfg", "autoUpdate=false");
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

    }
    public static void start() {

    }
}