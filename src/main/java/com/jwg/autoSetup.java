package com.jwg;

import com.jwg.jwgapi.errorHandler;
import com.jwg.jwgapi.logger;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.jwg.Main.*;
import static com.jwg.Main.project;
import static com.jwg.jwgapi.parseVersion.versionInt;
import static com.jwg.jwgapi.readFile.fileReadLine;
import static com.jwg.jwgapi.writeFile.overwriteFile;
import static java.lang.System.exit;

public class autoSetup {

    // TODO move these to jwgapi
    private static void ensureFileExists(String path) {
        try {
            File file = new File(path);
            if (file.createNewFile()) {
                logger.log(logFile, versionInt(version), project, 0, "Created file " + path + ".");
            } else {
                logger.log(logFile, versionInt(version), project, 0, "Couldn't create file " + path + ".");
            }
        } catch (IOException e) {
            errorHandler.handleError(e + " Fatal crash", "JWG MC Pre-Init", versionInt(version), logFile);
            exit(1);
        }
    }

    public static void ensureDirExists(String path) {
        try {
            Path dir = Paths.get(path);
            Files.createDirectories(dir);
            logger.log(logFile, versionInt(version), project, 0, "Created directory " + path + ".");
        } catch (IOException e) {
            logger.log(logFile, versionInt(version), project, 0, "Could not create directory" + path + "; does it already exist?");
        }
    }

    public static void setup() throws IOException {
        ensureFileExists("modloaderUpdate.cfg");
        ensureFileExists("settings.cfg");

        overwriteFile(
                "settings.cfg",
                "startPopup=true\ncracked=false\nenableCustomJVM=false\ncrackedIGN=Cracked IGN\nminRamAlloc=2G\nmaxRamAlloc=512M\njrePath="+System.getProperty("java.home")+"\ncustomArgs=Custom Args"
        );
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
            Path path = Paths.get("launcher/templates");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created templates/vanilla directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create template directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }
        try {
            Path path = Paths.get("launcher/modloaders");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created launcher/modloaders directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }
        try {
            Path path = Paths.get("launcher/modloaders/Forge");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created launcher/modloaders directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }
        try {
            Path path = Paths.get("launcher/modloaders/Fabric");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created launcher/modloaders directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }
        try {
            Path path = Paths.get("launcher/modloaders/Quilt");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created launcher/modloaders directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }
        try {
            Path path = Paths.get("launcher/modloaders/Vanilla");
            Files.createDirectories(path);
            logger.log(logFile, versionInt(version), project, 0, "Created launcher/modloaders directory.");
        } catch (IOException e) {
            errorHandler.handleError(e + " Could not create directory. Try going into the folder and removing everything except jar files.", "JWG MC Init", versionInt(version), logFile);
            exit(0);
        }

        logger.log(logFile, versionInt(version), project, 0, "Created all directories!");
        int reply = JOptionPane.showConfirmDialog(null, "Do you want to check for updated modloaders? (This only appears once, it's recommended to check for updated modloaders!)", "Update Modloaders?", JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.YES_OPTION) {
            overwriteFile("modloaderUpdate.cfg", "autoUpdate=true\n"+fileReadLine("settings.cfg", 1)+"\n"+fileReadLine("settings.cfg", 2)+"\n"+fileReadLine("settings.cfg", 3)+"\n"+fileReadLine("settings.cfg", 4)+"\n"+fileReadLine("settings.cfg", 5)+"\n"+fileReadLine("settings.cfg", 6)+"\n"+fileReadLine("settings.cfg", 7));
        } else {
            overwriteFile("modloaderUpdate.cfg", "autoUpdate=false\n"+fileReadLine("settings.cfg", 1)+"\n"+fileReadLine("settings.cfg", 2)+"\n"+fileReadLine("settings.cfg", 3)+"\n"+fileReadLine("settings.cfg", 4)+"\n"+fileReadLine("settings.cfg", 5)+"\n"+fileReadLine("settings.cfg", 6)+"\n"+fileReadLine("settings.cfg", 7));
        }
    }
}
