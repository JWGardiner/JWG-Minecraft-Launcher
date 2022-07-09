package com.jwg.jwgapi;

import java.io.*;
import java.util.Scanner;

public class writeFile {
    public static void overwriteFile(String filePath, String text) {
        try {
            FileWriter fileOverwriter = new FileWriter(filePath);
            fileOverwriter.write(text);
            fileOverwriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void replaceTextInFile(String filePath, String textToReplace, String finalText) {
        try
        {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            StringBuilder oldtext = new StringBuilder();
            while((line = reader.readLine()) != null)
            {
                oldtext.append(line).append("\r\n");
            }
            reader.close();
            String newtext = oldtext.toString().replaceAll(textToReplace, finalText);

            FileWriter writer = new FileWriter(filePath);
            writer.write(newtext);writer.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}