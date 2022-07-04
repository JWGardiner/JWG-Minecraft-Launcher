package com.jwg.jwgapi;


public class shorthands {
    public void debugLn(String text) {
        System.out.println(text);
    }
    public void debug(String text) {
        System.out.print(text);
    }
    public static Double randomizer(Double min, Double max) {
        return Math.floor(Math.random()*(max-min+1)+min);
    }
}