package com.jwg;

import com.jwg.jwgapi.*;

import javax.swing.*;
import java.io.*;
import java.net.MalformedURLException;
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
    public static void Init() throws IOException {
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

            URL versions = new URL("https://raw.githubusercontent.com/JWGardiner/JWG-Minecraft-Launcher/main/versions.md");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(versions.openStream()));

            String versionLine;
            int i = 0;
            while ((versionLine = in.readLine()) != null)  {
                i = i++;
                try {
                    File file = new File("launcher/templates/vanilla/"+versionLine);
                    if (!file.exists()) {
                        try {
                            Path path = Paths.get("launcher/templates/vanilla/"+versionLine);
                            Files.createDirectories(path);

                        } catch (IOException e) {
                            errorHandler.handleError(e+ " Fatal Crash", "JWG MC Pre-Init", versionInt(version), logFile);
                            exit(0);
                        }
                        URL website = new URL("https://launcher.mojang.com/v1/objects/c0898ec7c6a5a2eaa317770203a1554260699994/client.jar");
                        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
                        FileOutputStream fos = new FileOutputStream("launcher/templates/vanilla/"+versionLine+"/"+versionLine+".jar");
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    }

                } catch(Exception e) {
                    errorHandler.handleError(e+ " Fatal Crash", "JWG MC Pre-Init", versionInt(version), logFile);
                    exit(0);
                }
            }
            in.close();

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