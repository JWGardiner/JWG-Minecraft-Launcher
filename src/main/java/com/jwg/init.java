package com.jwg;

import com.jwg.jwgapi.*;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

import static com.jwg.Main.*;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static java.lang.System.exit;

public class init {
    static boolean needsSetup = false;
    static boolean hasInternetConnection = false;
    static boolean updateModloader = false;

    private static boolean checkInternetConnection() {
        try {
            URL url = new URL("https://piston-meta.mojang.com");
            URLConnection connection = url.openConnection();
            connection.connect();

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void preInit() {
        if(!sysinfo.getOs().equals("linux") && !sysinfo.getOs().equals("windows")) {
            errorHandler.handleError("Unsupported OS: The program might still work. Proceed with caution. Logs from your installation are invalid.", "JWG MC Pre-Init", versionInt(version), logFile);
            logger.log(logFile, versionInt(version), project, 2, "LOGS ARE INVALID: UNSUPPORTED OS");
        }
        if(!new File("launcher").isDirectory()) {
            needsSetup = true;
        }

        hasInternetConnection = checkInternetConnection();
        if (hasInternetConnection) {
            logger.log(logFile, versionInt(version), project, 0, "User is connected to the internet.");
        } else {
            logger.log(logFile, versionInt(version), project, 1, "User is not connected to the internet (or Mojang's servers are down).");
        }

        if (needsSetup && !hasInternetConnection) {
            try {
                URL checkConnectionGit = new URL("http://www.github.com");
                URLConnection connection = checkConnectionGit.openConnection();
                connection.connect();
                logger.log(logFile, versionInt(version), project, 0, "User is connected to WiFi. - Google is down.");
                hasInternetConnection = true;
            } catch (IOException e) {
                logger.log(logFile, versionInt(version), project, 1, "User is not connected to WiFi. - Or Google & GitHub are down.");
                errorHandler.handleError("The launcher may be unable to auto-install; it cannot access google.com or github.com. Please check your WiFi connection - It will continue but may crash or have unpredictable behaviour without a WiFi connection! ", "JWG MC Pre-Init", versionInt(version), logFile);
            }
        }
    }
    public static void init() {
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
            autoSetup.ensureDirExists("launcher/assets");
            autoSetup.ensureDirExists("launcher/modloaders/fabric");
            autoSetup.ensureDirExists("launcher/modloaders/forge");
            autoSetup.ensureDirExists("launcher/modloaders/quilt");
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