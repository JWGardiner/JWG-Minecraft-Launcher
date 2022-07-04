package com.jwg.jwgapi;

import javax.swing.*;

public class swingUtils {
    public static void configureJframe(JFrame frame, int heightX, int heightY, int locationX, int locationY, Boolean isResizeable, Boolean exitOnClose) {
        frame.setSize(heightX,heightY);
        frame.setResizable(isResizeable);
        frame.setLocation(locationX, locationY);

        if (exitOnClose) {
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }
}