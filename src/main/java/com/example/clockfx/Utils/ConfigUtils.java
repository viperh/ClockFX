package com.example.clockfx.Utils;

import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@SuppressWarnings("all")
public abstract class ConfigUtils {


    private static final String name = "config.yml";

    private static final String tempDir = System.getProperty("java.io.tmpdir");

    private static final String path = Paths.get(tempDir, name).toString();

    private static final String defaultStringContent = """
            general-color : %s
            clock-color : %s
            bg-color : %s
            controls-color : %s
            hidden-controls : %s
            """.format(parseColor(Settings.getGeneralColor()),
            parseColor(Settings.getClockColor()),
            parseColor(Settings.getBgColor()),
            parseColor(Settings.getControlsColor()),
            String.valueOf(Settings.areControlsHidden()));


    public static String parseColor(Color a){

        return "#" + a.toString().substring(2,8);

    }

    private static HashMap<String, String> config = new HashMap<>();


    public static boolean configExists(){
        return Files.exists(Paths.get(path));
    }


    public static void createConfig(String generalColor, String clockColor, String bgColor, String controlsColor, String hiddenControls) {
        try {

            if(configExists()){
                Files.delete(Path.of(path));
            }

            Files.createFile(Path.of(path));


            String content = """
                    general-color : %s
                    clock-color : %s
                    bg-color : %s
                    controls-color : %s
                    hidden-controls : %s
                    """.formatted(generalColor, clockColor, bgColor, controlsColor, hiddenControls);

            FileWriter fw = new FileWriter(path);
            fw.write(content);
            fw.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void createDefaultConfig() {
        try{
            Files.createFile(Path.of(path));
            FileWriter fw = new FileWriter(path);
            fw.write(defaultStringContent);
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void loadConfig(){
        if(!configExists()){
            createDefaultConfig();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String line;
            while ((line = br.readLine()) != null){
                if (line.isEmpty()){
                    continue;
                }
                config.put(line.split(":")[0].trim(), line.split(":")[1].trim());
            }

            applySettings();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }





    public static void applySettings(){
        for (HashMap.Entry<String, String> entry : config.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key){
                case "general-color" -> {
                    Settings.setGeneralColor(Color.valueOf(value));
                }
                case "clock-color" -> {
                    Settings.setClockColor(Color.valueOf(value));
                }
                case "controls-color" -> {
                    Settings.setControlsColor(Color.valueOf(value));
                }
                case "bg-color" -> {
                    Settings.setBgColor(Color.valueOf(value));
                }
                case "hide-controls" -> {
                    Settings.setHiddenControls(Boolean.parseBoolean(value));
                }
            }
        }
    }


}
