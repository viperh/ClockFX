package com.example.clockfx.WebScraper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static String finalPath = System.getProperty("java.io.tmpdir");

    public static boolean fileExists(String name){
        Path newPath = Paths.get(finalPath, name);
        return Files.exists(newPath);
    }

    public static String getFinalPath(String name){
        return Paths.get(finalPath, name).toString();
    }

    public static void createFile(String name){
        Path newPath = Paths.get(finalPath, name);
        try {
            Files.createFile(newPath);
        } catch (IOException ignored) {

        }
    }

    public static void delFile(String file) {
        Path newPath = Paths.get(finalPath, file);
        try {
            Files.delete(newPath);
        } catch (IOException ignored) {
        }
    }

    
}
