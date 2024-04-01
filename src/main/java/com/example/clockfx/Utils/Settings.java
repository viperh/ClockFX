package com.example.clockfx.Utils;

import javafx.scene.paint.Color;
@SuppressWarnings("unused")
public abstract class Settings {

    private static Color bgColor = Color.BLACK;

    private static Color generalColor = Color.RED;
    private static Color clockColor = Color.RED;
    private static Color controlsColor = Color.RED;

    private static boolean hiddenControls = false;

    public static Color getBgColor(){
        return bgColor;
    }
    public static void setBgColor(Color bg){
        bgColor = bg;
    }

    public static Color getGeneralColor(){
        return generalColor;
    }
    public static void setGeneralColor(Color gC){
        generalColor = gC;
    }

    public static Color getControlsColor(){
        return controlsColor;
    }
    public static void setControlsColor(Color cC){
        controlsColor = cC;
    }

    public static boolean areControlsHidden(){
        return hiddenControls;
    }

    public static void setHiddenControls(boolean h){
        hiddenControls = h;
    }


    public static Color getClockColor() {
        return clockColor;
    }

    public static void setClockColor(Color cC){
        clockColor = cC;
    }

}
