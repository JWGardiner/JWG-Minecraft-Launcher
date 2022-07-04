package com.jwg;

import com.jwg.jwgapi.errorHandler;
import com.jwg.jwgapi.logger;
import com.jwg.jwgapi.writeFile;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jwg.Main.*;
import static com.jwg.Main.project;
import static com.jwg.init.frame;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static com.jwg.jwgapi.swingUtils.configureJframe;
import static java.lang.System.exit;

public class autoSetup {
    public static void setup() {
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
}