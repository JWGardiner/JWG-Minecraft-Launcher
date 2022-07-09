package com.jwg.jwgapi;

import com.jwg.Main;

import java.io.File;
import java.io.IOException;

public class cfgFileUtils {
    public static void addCFG(String name, String file, String value) {
        String wholefile;
        try {
             wholefile = readFile.fileRead(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        wholefile = wholefile + "\n"+name+"="+value;

        File fileToDelete = new File(file);
        if (fileToDelete.delete()) {
            try {
                if (fileToDelete.createNewFile()) {
                    writeFile.overwriteFile(file,wholefile);
                }
            } catch (IOException e) {
                errorHandler.handleError(e + " Fatal Error", "jwgapi", parseVersion.versionInt(Main.version), "jwgapi.log");
            }
        }
        ;
    }
    public static String readCFG(int line, String file) {
        String fileLine;
        try {
            fileLine = readFile.fileReadLine(file, line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return fileLine.substring(fileLine.indexOf("=") + 1).trim();
    }
}