package com.jwg;

import com.jwg.jwgapi.errorHandler;
import com.jwg.jwgapi.logger;
import com.jwg.jwgapi.sysinfo;

import static com.jwg.jwgapi.parseVersion.versionInt;

public class Main {
    public static String logFile = "latest.log";
    public static String project = "JWG-MC-Launcher";
    public static String version = "0.0.1";

    public static void main(String[] args) {
        long initStartTime = System.currentTimeMillis();
        logger.StartLogger(logFile);
        logger.log(logFile, versionInt(version), project, 0, "Starting JWG-Minecraft Launcher...");
        logger.log(logFile, versionInt(version), project, 0, "Starting Pre-Initialisation");
        launcher.preInit();
        logger.log(logFile, versionInt(version), project, 0, "Pre Initialisation Finished!");
        logger.log(logFile, versionInt(version), project, 0, "Initialising...");
        launcher.Init();
        long initTook = System.currentTimeMillis()-initStartTime;
        logger.log(logFile, versionInt(version), project, 0, "Done! Took "+initTook+"ms");
        launcher.start();
    }
}