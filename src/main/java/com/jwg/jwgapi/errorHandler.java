package com.jwg.jwgapi;

import javax.swing.*;
import java.awt.*;

import static com.jwg.jwgapi.parseVersion.versionInt;

public class errorHandler {
    public static void handleError(String e, String whatCrashed, int version, String logFile) {
            logger.log(logFile, versionInt(String.valueOf(version)), whatCrashed, 3, "JWG Crash handler: "+e);
            Frame f=new JFrame();
            JOptionPane.showMessageDialog(f,"Error: "+e,whatCrashed+": "+version+" has crashed.",JOptionPane.ERROR_MESSAGE);
        }
}