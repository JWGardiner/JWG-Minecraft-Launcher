package com.jwg.jwgapi;

public class parseVersion {
    public static int versionInt(String version) {
        version = version.replaceAll("[.]","");
        //eg version 1.9.3 will return 193
        return Integer.parseInt(version);
    }
}