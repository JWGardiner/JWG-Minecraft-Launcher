package com.jwg.jwgapi;

public class sysinfo {
    public static String getOs() {
        String os = System.getProperty("os.name");
        String finalOS = "";
        if (os.toLowerCase().startsWith("windows")) {
            finalOS = "windows";
        } else if (os.toLowerCase().startsWith("linux")) {
            finalOS = "linux";
        } else {
            finalOS = "unknown";
        }
        return finalOS;
    }
    public static String getUser() {
        return System.getProperty("user.name");
    }
    public static String getOsArchitecture() {
        return System.getProperty("os.arch");
    }

}